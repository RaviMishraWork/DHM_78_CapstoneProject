package com.example.demo.service;


import com.example.demo.client.ProductClient;
import com.example.demo.client.UserAuthClient;
import com.example.demo.data.*;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SeedService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryStockRepository inventoryStockRepository;
    @Autowired
    private SaleProductRepository saleProductRepository;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private UserAuthClient userAuthClient;
    @Autowired
    private ProductClient productClient;

    private List<ProductEntity> products = new ArrayList<>();
    private List<InventoryStockEntity> inventoryStocks = new ArrayList<>();
    private List<OrderProduct> orderProducts = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<SaleProduct> saleProducts = new ArrayList<>();
    private List<Sale> sales = new ArrayList<>();
    private List<SupplierEntity> suppliers = new ArrayList<>();
    private List<AuthRequest> users = new ArrayList<AuthRequest>();


    @Transactional
    public void seedAll() {
        this.seed();
    }

    public void seed() {
        generateUsers();
        generateSuppliers();
        if (suppliers.isEmpty())
            return;
//        generateProducts();
        addSeedProducts();
        if (products.isEmpty())
            return;
//        generateInventoryStocks();
        generateOrders((products.size() * 9));
        if (orders.isEmpty())
            return;
        generateSales((products.size() * 9));
        if (sales.isEmpty())
            return;
    }

    public void generateUsers(){
        List<String> names = List.of("Oleg", "Maxime", "Regina", "Klaus", "Zon", "Nico", "Luka", "Inigo", "Sutja", "Jordan", "Anna", "Evonne", "Vanya", "Tomas", "Gwen", "Capella");
        List<String> emails = new ArrayList<String>();
        List<AuthRequest> requests = new ArrayList<AuthRequest>();
        for (int i = 0; i < names.size(); i++) {
            String email = names.get(i) + "@m-m.institute";
            emails.add(email);
            AuthRequest request = new AuthRequest();
            request.setUsername(email);
            request.setPassword("password");
            Role role = Role.values()[new Random().nextInt(Role.values().length)];
            request.setRole(role);
            request.setAlertsEnabled(false);
            requests.add(request);
            UserCredential userCredential = new UserCredential();
            userCredential.setName(names.get(i));
            userCredential.setEmail(email);
            userCredential.setPassword("password");
            userCredential.setRole(role);
            userCredential.setAlertsEnabled(false);
            AuthResponse response = userAuthClient.register(userCredential);
            if (response.isSuccessful()) {
                users.add(request);
            }
        }
    }

    public void generateProducts(){
        List<String> adjectives = List.of("Red", "Blue", "Green", "Yellow", "Black", "White", "Used", "New", "Old", "Modern");
        List<String> nouns = List.of("Shirt", "Pants", "Shoes", "Hat", "Bag", "Watch", "Glasses", "Phone", "Laptop", "Tablet");
        List<String> descriptionFormats = List.of(
                "This is a %s %s perfect for any occasion and lifestyle.",
                "Introducing the %s %s, a must-have for your wardrobe.",
                "Experience the comfort of the %s %s, designed for everyday wear.",
                "The %s %s combines style and functionality in one package.",
                "Elevate your look with the %s %s, a timeless classic.",
                "The %s %s is the perfect accessory for any outfit.",
                "Stay trendy with the %s %s, a fashion-forward choice."
        );

        for (int i = 0; i < 10; i++) {
            ProductEntity product = new ProductEntity();
            int newSKU = new Random().nextInt(0,Integer.MAX_VALUE);
            while (productRepository.findBySku(newSKU) != null) {
                newSKU = new Random().nextInt(0,Integer.MAX_VALUE);
            }
            product.setSku(newSKU);
            String adjective = adjectives.get(new Random().nextInt(adjectives.size()));
            String noun = nouns.get(new Random().nextInt(nouns.size()));
            product.setName(adjective + " " + noun);
            product.setDescription(String.format(descriptionFormats.get(new Random().nextInt(descriptionFormats.size())), adjective, noun));
            product.setBuyPrice(new Random().nextDouble(10, 100));
            product.setSellPrice(new Random().nextDouble(10, 100) + product.getBuyPrice());
            product.setInitial_stock(new Random().nextInt(1, 15));
            product.setSupplierId(suppliers.get(new Random().nextInt(suppliers.size())).getId());
//            product = productRepository.save(product);
            ProductDTO productDTO = mapProductEntityToProductDTO(product);
            productDTO = productClient.addProduct(productDTO);
            if (productDTO != null) {
                products.add(product);
            }
        }
    }
    public void addSeedProducts(){
        List<Product> seedProducts = getSeedProducts();
        for (Product product : seedProducts) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setSku(new Random().nextInt(0,Integer.MAX_VALUE));
            productEntity.setName(product.name);
            productEntity.setDescription(product.description);
            productEntity.setCategory(product.category);
            productEntity.setBuyPrice(product.buyingPrice);
            productEntity.setSellPrice(product.sellingPrice);
            productEntity.setInitial_stock(new Random().nextInt(1, 15));
            productEntity.setSupplierId(suppliers.get(new Random().nextInt(suppliers.size())).getId());
//            productRepository.save(productEntity);
            ProductDTO productDTO = mapProductEntityToProductDTO(productEntity);
            productDTO = productClient.addProduct(productDTO);
            if (productDTO != null) {
                products.add(productEntity);
            }
        }
    }
    public void generateInventoryStocks() {
        for (ProductEntity product : products) {
            InventoryStockEntity inventoryStock = new InventoryStockEntity();
            inventoryStock.setSku(product.getSku());
            inventoryStock.setQuantity(product.getInitial_stock());
            inventoryStock = inventoryStockRepository.save(inventoryStock);
            inventoryStocks.add(inventoryStock);
        }
    }

    public void generateSuppliers() {
        List<String> names = List.of("Amazon", "Shenzhen", "Alibaba", "Redbubble", "Blackrock", "Etsy", "TotalGoods", "HomeRun", "Goodwill");
        List<String> addresses = List.of("123 Main St", "456 Elm St", "789 Oak St", "101 Pine St", "202 Maple St");
        List<String> phones = List.of("1234567890", "0987654321", "5555555555", "1112223333", "4445556666");
        List<String> contacts = List.of("John Doe", "Jane Smith", "Alice Johnson", "Bob Brown", "Charlie Davis");
        List<String> contactInfos = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            String contactInfoString = contacts.get(new Random().nextInt(contacts.size())) + "\n" + phones.get(new Random().nextInt(phones.size())) + "\n" + addresses.get(new Random().nextInt(addresses.size()));
            contactInfos.add(contactInfoString);
        }
        for (int i = 0; i < names.size(); i++) {
            SupplierEntity supplier = new SupplierEntity();
//            supplier.setName(names.get(new Random().nextInt(names.size())));
            supplier.setName(names.get(i));
//            supplier.setContactInfo(contactInfos.get(new Random().nextInt(contactInfos.size())));
            supplier.setContactInfo(contactInfos.get(i));
            supplier.setRating(String.valueOf(new Random().nextDouble(1, 5)));
            supplier = supplierRepository.save(supplier);
            suppliers.add(supplier);
        }
    }

//    public static class Supplier {
//        String name;
//        String contactPerson;
//        String phoneNumber;
//        String address;
//
//        public Supplier(String name, String contactPerson, String phoneNumber, String address) {
//            this.name = name;
//            this.contactPerson = contactPerson;
//            this.phoneNumber = phoneNumber;
//            this.address = address;
//        }
//
//        @Override
//        public String toString() {
//            return String.format("%s\n  Contact: %s\n  Phone: %s\n  Address: %s\n",
//                    name, contactPerson, phoneNumber, address);
//        }
//    }


    public void generateOrders(int numberOfOrders) {
        suppliers = supplierRepository.findAll();
        products = productRepository.findAll();
        for (int i = 0; i < numberOfOrders; i++) {
            Order order = new Order();
            SupplierEntity supplier = suppliers.get(new Random().nextInt(suppliers.size()));
            order.setSupplierId(supplier.getId());
//            order.setTotalPrice(new Random().nextDouble(100, 1000));
            order.setOrderStatus(OrderStatus.PENDING);
            List<OrderProduct> orderProductList = generateOrderProducts();
            order.setTotalPrice(orderProductList.stream().mapToDouble(op -> op.getPrice() * op.getQuantity()).sum());
            orderProductList.stream().forEach(order::addProduct);
            order.setOrderStatus(OrderStatus.values()[new Random().nextInt(OrderStatus.values().length)]);
            long howLongAgo = new Random().nextLong(1L, 1000L);
            long dayFactor = 24L * 60L * 60L * 1000L;
            order.setOrderCreated(new java.sql.Date(System.currentTimeMillis() - howLongAgo * dayFactor));
            if (howLongAgo > 1) {
                howLongAgo = new Random().nextLong(1L, howLongAgo);
                if (order.getOrderStatus() != OrderStatus.PENDING) {
                    order.setOrderUpdated(new java.sql.Date(System.currentTimeMillis() - howLongAgo * dayFactor));
                }
            }
            if (howLongAgo > 1) {
                howLongAgo = new Random().nextLong(1L, howLongAgo);
                if (order.getOrderStatus() == OrderStatus.COMPLETED) {
                    order.setOrderCompleted(new java.sql.Date(System.currentTimeMillis() - howLongAgo * dayFactor));
                }
            }

            //order.setProducts(orderProductList);
            order = orderRepository.save(order);
            orders.add(order);
        }
    }

    private List<OrderProduct> generateOrderProducts(){
        int limit = new Random().nextInt(1, 5);
        List<OrderProduct> orderProductList = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            ProductEntity product = products.get(new Random().nextInt(products.size()));
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setQuantity(new Random().nextInt(1, 10));
            orderProduct.setSku(product.getSku());
            orderProduct.setName(product.getName());
            orderProduct.setPrice(product.getSellPrice());
            if (orderProductList.stream().anyMatch(op -> op.getSku() == orderProduct.getSku())) {
                continue;
            }
            orderProductList.add(orderProduct);
        }
        return orderProductList;
    }

    public void generateSales(int numberOfSales) {
        products = productRepository.findAll();
        for (int i = 0; i < numberOfSales; i++) {
            Sale sale = new Sale();
//            sale.setTotalPrice(new Random().nextDouble(100, 1000));
            sale.setSaleStatus(SaleStatus.PENDING);
            List<String> userRoleUsernames;
            if (!users.isEmpty()) {
                userRoleUsernames = users.stream().filter(user -> user.getRole() == Role.USER).map(user -> user.getUsername()).toList();
            }
            else {
                userRoleUsernames = userAuthClient.getAllUsers();
            }
            String randomUsername = userRoleUsernames.get(new Random().nextInt(userRoleUsernames.size()));
            sale.setCustomerUsername(randomUsername);
            List<SaleProduct> saleProductList = generateSaleProducts();
            sale.setTotalPrice(saleProductList.stream().mapToDouble(sp -> sp.getPrice() * sp.getQuantity()).sum());
            saleProductList.stream().forEach(
                    sale::addProduct
            );
            sale.setSaleStatus(SaleStatus.values()[new Random().nextInt(SaleStatus.values().length)]);
            long howLongAgo = new Random().nextLong(1L, 1000L);
            long dayFactor = 24L * 60L * 60L * 1000L;
            sale.setSaleCreated(new java.sql.Date(System.currentTimeMillis() - howLongAgo * dayFactor));
            if (howLongAgo > 1) {
                howLongAgo = new Random().nextLong(1L, howLongAgo);
                if (sale.getSaleStatus() != SaleStatus.PENDING) {
                    sale.setSaleUpdated(new java.sql.Date(System.currentTimeMillis() - howLongAgo * dayFactor));
                }
            }
            if (howLongAgo > 1) {
                howLongAgo = new Random().nextLong(1L, howLongAgo);
                if (sale.getSaleStatus() == SaleStatus.COMPLETED) {
                    sale.setSaleCompleted(new java.sql.Date(System.currentTimeMillis() - howLongAgo * dayFactor));
                }
            }

//            sale.setProducts(saleProductList);
            sale = saleRepository.save(sale);
            sales.add(sale);
        }
    }
    private List<SaleProduct> generateSaleProducts(){
        int limit = new Random().nextInt(1, 5);
        List<SaleProduct> saleProductList = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            ProductEntity product = products.get(new Random().nextInt(products.size()));
            SaleProduct saleProduct = new SaleProduct();
            saleProduct.setQuantity(new Random().nextInt(1, 10));
            saleProduct.setSku(product.getSku());
            saleProduct.setName(product.getName());
            saleProduct.setPrice(product.getSellPrice());
            if (saleProductList.stream().anyMatch(sp -> sp.getSku() == saleProduct.getSku())) {
                continue;
            }
            saleProductList.add(saleProduct);
        }
        return saleProductList;
    }
    private ProductDTO mapProductEntityToProductDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setSku(productEntity.getSku());
        productDTO.setName(productEntity.getName());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setBuyPrice(productEntity.getBuyPrice());
        productDTO.setSellPrice(productEntity.getSellPrice());
        productDTO.setInitial_stock(productEntity.getInitial_stock());
        productDTO.setSupplierId(productEntity.getSupplierId());
        productDTO.setImageUrl(productEntity.getImageUrl());
        productDTO.setCategory(productEntity.getCategory());
        return productDTO;
    }
    public static class Product {
        String name;
        String description;
        double buyingPrice;
        double sellingPrice;
        String category;

        public Product(String name, String description, double buyingPrice, double sellingPrice, String category) {
            this.name = name;
            this.description = description;
            this.buyingPrice = buyingPrice;
            this.sellingPrice = sellingPrice;
            this.category = category;
        }

        @Override
        public String toString() {
            return String.format("%s\n  Description: %s\n  Buying Price: $%.2f\n  Selling Price: $%.2f\n Category: %s\n",
                    name, description, buyingPrice, sellingPrice, category);
        }
    }

    public static List<Product> getSeedProducts() {
        List<Product> products = new ArrayList<>();

        // Electronics
        products.add(new Product("Sony WH-1000XM5 Wireless Headphones", "Industry-leading noise canceling headphones with premium sound quality and long battery life.", 230.00, 399.99, "Electronics"));
        products.add(new Product("Apple Watch Series 9", "Advanced smartwatch with fitness tracking, ECG, and seamless integration with iPhone.", 320.00, 429.00, "Electronics"));
        products.add(new Product("Samsung Galaxy Tab S9", "High-performance Android tablet with a vivid AMOLED display and S Pen support.", 500.00, 699.99, "Electronics"));
        products.add(new Product("JBL Flip 6 Bluetooth Speaker", "Portable waterproof speaker with powerful bass and 12 hours of playtime.", 70.00, 129.95, "Electronics"));
        products.add(new Product("SanDisk 1TB External SSD", "Compact, durable solid-state drive with fast transfer speeds and 1TB capacity.", 90.00, 139.99, "Electronics"));
        products.add(new Product("DJI Mini 3 Pro Drone", "Lightweight drone with 4K video, obstacle sensing, and intelligent flight modes.", 600.00, 759.00, "Electronics"));
        products.add(new Product("Anker Wireless Charging Pad", "Fast wireless charger compatible with Qi-enabled devices, with a slim, sleek design.", 12.00, 19.99, "Electronics"));
        products.add(new Product("TP-Link Archer AX55 Wi-Fi Router", "Dual-band Wi-Fi 6 router with high-speed performance and wide coverage.", 80.00, 129.99, "Electronics"));

        // Fashion & Apparel
        products.add(new Product("Nike Air Max 270", "Stylish running shoes with visible Air cushioning and breathable mesh upper.", 90.00, 160.00,"Fashion & Apparel"));
        products.add(new Product("Levi’s Trucker Denim Jacket", "Classic denim jacket with a regular fit, button-front closure, and signature Levi’s style.", 45.00, 89.50,"Fashion & Apparel"));
        products.add(new Product("Herschel Little America Backpack", "Modern laptop backpack with a spacious design and signature striped liner.", 60.00, 109.99,"Fashion & Apparel"));
        products.add(new Product("The North Face ThermoBall Vest", "Insulated, lightweight vest ideal for layering in cold conditions.", 80.00, 149.00,"Fashion & Apparel"));
        products.add(new Product("Adidas Ultraboost 22", "High-performance running shoes with responsive cushioning and adaptive knit upper.", 95.00, 190.00,"Fashion & Apparel"));
        products.add(new Product("Columbia Newton Ridge Hiking Boots", "Durable waterproof hiking boots with great traction and ankle support.", 65.00, 99.99,"Fashion & Apparel"));
        products.add(new Product("Carhartt Rugged Flex Cargo Pants", "Tough, flexible work pants with multiple pockets for tools and gear.", 30.00, 54.99,"Fashion & Apparel"));
        products.add(new Product("Ray-Ban Wayfarer Sunglasses", "Iconic sunglasses with a durable frame and polarized lens options.", 80.00, 163.00,"Fashion & Apparel"));

        // Home & Living
        products.add(new Product("COSORI Electric Kettle", "Fast-boiling stainless steel electric kettle with auto shut-off and boil-dry protection.", 25.00, 39.99, "Home & Living"));
        products.add(new Product("Brooklinen Luxe Core Sheet Set", "Luxury cotton sheets with a silky-smooth finish and long-lasting comfort.", 85.00, 159.00, "Home & Living"));
        products.add(new Product("TaoTronics LED Desk Lamp", "Dimmable LED desk lamp with touch controls and USB charging port.", 18.00, 35.99, "Home & Living"));
        products.add(new Product("Caraway Nonstick Cookware Set", "Eco-friendly nonstick pots and pans with a ceramic coating and modern design.", 250.00, 395.00, "Home & Living"));
        products.add(new Product("Yankee Candle Balsam & Cedar", "Classic holiday-scented candle with a long-lasting, fresh woodsy fragrance.", 15.00, 28.00, "Home & Living"));
        products.add(new Product("Brita Water Filter Pitcher", "Water filtration pitcher that reduces chlorine, lead, and other impurities.", 18.00, 36.99, "Home & Living"));
        products.add(new Product("UGG Avery Reversible Throw Blanket", "Soft and cozy reversible blanket made from plush polyester fleece.", 20.00, 49.99, "Home & Living"));
        products.add(new Product("Umbra Conceal Floating Bookshelves", "Minimalist floating shelves that create the illusion of books hanging on the wall.", 12.00, 24.00, "Home & Living"));

        // Beauty & Wellness
        products.add(new Product("The Ordinary Niacinamide 10% + Zinc 1%", "High-strength serum that targets blemishes and balances oil production.", 4.00, 7.50, "Beauty & Wellness"));
        products.add(new Product("Vitruvi Stone Diffuser", "Ceramic essential oil diffuser with ultrasonic technology for calming aromas.", 75.00, 123.00, "Beauty & Wellness"));
        products.add(new Product("Olaplex No. 3 Hair Perfector", "At-home treatment that repairs damaged hair and strengthens from within.", 20.00, 30.00, "Beauty & Wellness"));
        products.add(new Product("Native Coconut & Vanilla Deodorant", "Aluminum-free natural deodorant with a refreshing coconut vanilla scent.", 6.00, 12.00, "Beauty & Wellness"));
        products.add(new Product("Revlon Jade Facial Roller", "Dual-ended jade roller that helps reduce puffiness and improve skin tone.", 6.00, 15.99, "Beauty & Wellness"));
        products.add(new Product("Ritual Essential Multivitamins", "Daily vitamins with clean ingredients and traceable sourcing, tailored for adults.", 20.00, 33.00, "Beauty & Wellness"));
        products.add(new Product("CeraVe Moisturizing Lotion", "Dermatologist-recommended lotion with ceramides for all-day hydration.", 8.00, 16.99, "Beauty & Wellness"));
        products.add(new Product("Neutrogena Foaming Facial Cleanser", "Gentle foaming cleanser that removes oil and makeup without over-drying.", 4.00, 9.49, "Beauty & Wellness"));

        // Toys & Games
        products.add(new Product("LEGO Classic Creative Bricks Set", "Colorful building blocks that encourage open-ended creativity and play.", 25.00, 39.99, "Toys & Games"));
        products.add(new Product("Traxxas Rustler RC Car", "High-speed remote-controlled car built for off-road and on-road fun.", 140.00, 199.99, "Toys & Games"));
        products.add(new Product("Squishmallows Plush Toy", "Super-soft, collectible stuffed animals in a variety of characters and sizes.", 10.00, 19.99, "Toys & Games"));
        products.add(new Product("Ravensburger Disney Puzzle", "High-quality jigsaw puzzle featuring beloved Disney characters and artwork.", 8.00, 16.99, "Toys & Games"));
        products.add(new Product("Crayola Inspiration Art Case", "Comprehensive art set with crayons, markers, pencils, and paper in a carry case.", 18.00, 24.99, "Toys & Games"));
        products.add(new Product("Sphero Mini App-Controlled Robot", "Programmable mini robot ball with games and coding activities for kids.", 40.00, 79.99, "Toys & Games"));
        products.add(new Product("UNO Card Game", "Classic family card game with wild cards, strategy, and fast-paced action.", 3.00, 6.99, "Toys & Games"));
        products.add(new Product("Jurassic World Action Figures", "Dinosaur action figure set inspired by the Jurassic World film series.", 12.00, 24.99, "Toys & Games"));

        // More Electronics
        products.add(new Product("Bose QuietComfort Earbuds II", "Noise-canceling wireless earbuds with customizable fit and superior audio quality.", 210.00, 299.00, "Electronics"));
        products.add(new Product("Amazon Fire HD 10 Tablet", "Affordable 10-inch tablet with full HD display and Alexa integration.", 80.00, 149.99, "Electronics"));
        products.add(new Product("Garmin Forerunner 255", "GPS running watch with heart rate monitoring and advanced metrics.", 220.00, 349.99, "Electronics"));
        products.add(new Product("Logitech MX Master 3S Mouse", "Precision wireless mouse with customizable buttons and ergonomic design.", 70.00, 99.99, "Electronics"));
        products.add(new Product("Roku Streaming Stick 4K", "Compact streaming device with voice control and Dolby Vision support.", 30.00, 49.99, "Electronics"));
        products.add(new Product("Google Nest Hub 2nd Gen", "Smart display with voice assistant and sleep tracking features.", 45.00, 99.99, "Electronics"));
        products.add(new Product("Canon EOS Rebel T7 DSLR Camera", "Entry-level DSLR camera with 24.1MP sensor and Wi-Fi connectivity.", 350.00, 479.99, "Electronics"));
        products.add(new Product("Fitbit Charge 6", "Fitness tracker with heart rate, sleep, and stress monitoring.", 90.00, 159.95, "Electronics"));

        // More Fashion & Apparel
        products.add(new Product("Converse Chuck Taylor All Star", "Classic canvas sneakers with rubber sole and timeless design.", 35.00, 65.00,"Fashion & Apparel"));
        products.add(new Product("Patagonia Better Sweater Fleece Jacket", "Warm fleece jacket made from recycled materials.", 70.00, 139.00,"Fashion & Apparel"));
        products.add(new Product("Fossil Leather Bifold Wallet", "Slim and stylish wallet made of genuine leather.", 20.00, 39.99,"Fashion & Apparel"));
        products.add(new Product("Birkenstock Arizona Sandals", "Comfortable sandals with cork footbed and adjustable straps.", 55.00, 99.99,"Fashion & Apparel"));
        products.add(new Product("Uniqlo Heattech Turtleneck", "Lightweight thermal turtleneck perfect for layering.", 10.00, 19.99,"Fashion & Apparel"));
        products.add(new Product("Timberland 6-Inch Premium Boots", "Waterproof leather boots with rugged sole and padded collar.", 110.00, 198.00,"Fashion & Apparel"));
        products.add(new Product("Lululemon Align High-Rise Leggings", "Soft, breathable leggings designed for yoga and lounging.", 45.00, 98.00,"Fashion & Apparel"));
        products.add(new Product("Under Armour Tech T-Shirt", "Moisture-wicking athletic t-shirt with a comfortable fit.", 10.00, 24.99,"Fashion & Apparel"));

        // More Home & Living
        products.add(new Product("Instant Pot Duo 7-in-1", "Multi-use pressure cooker with 7 functions including slow cooking and rice.", 60.00, 119.99, "Home & Living"));
        products.add(new Product("OXO Good Grips Salad Spinner", "Efficient salad spinner with non-slip base and easy-to-use design.", 20.00, 29.95, "Home & Living"));
        products.add(new Product("IKEA LACK Coffee Table", "Minimalist table with lightweight construction and modern design.", 15.00, 39.99, "Home & Living"));
        products.add(new Product("Keurig K-Supreme Coffee Maker", "Single-serve coffee maker with multiple cup size options and strong brew.", 80.00, 129.99, "Home & Living"));
        products.add(new Product("Philips Hue White Smart Bulbs", "Smart LED bulbs that work with Alexa and Google Assistant.", 30.00, 49.99, "Home & Living"));
        products.add(new Product("Shark Navigator Lift-Away Vacuum", "Upright vacuum with detachable canister for portable cleaning.", 120.00, 199.99, "Home & Living"));
        products.add(new Product("Simplehuman Sensor Trash Can", "Touch-free stainless steel trash can with motion sensor.", 70.00, 139.99, "Home & Living"));
        products.add(new Product("Nest Learning Thermostat", "Smart thermostat that adapts to your schedule and saves energy.", 170.00, 249.00, "Home & Living"));

        // More Beauty & Wellness
        products.add(new Product("Paula’s Choice BHA Liquid Exfoliant", "Salicylic acid exfoliant that unclogs pores and smooths skin texture.", 20.00, 34.00, "Beauty & Wellness"));
        products.add(new Product("Drunk Elephant Protini Moisturizer", "Peptide-rich face cream that improves skin texture and firmness.", 45.00, 68.00, "Beauty & Wellness"));
        products.add(new Product("Batiste Dry Shampoo", "Quick-refresh dry shampoo that absorbs oil and boosts volume.", 3.00, 8.99, "Beauty & Wellness"));
        products.add(new Product("Philips Sonicare ProtectiveClean 6100", "Electric toothbrush with pressure sensor and smart timer.", 70.00, 129.95, "Beauty & Wellness"));
        products.add(new Product("Aveeno Daily Moisturizing Lotion", "Oatmeal-infused body lotion for nourishing and soothing dry skin.", 5.00, 10.99, "Beauty & Wellness"));
        products.add(new Product("La Roche-Posay Anthelios Sunscreen SPF 60", "Lightweight sunscreen for sensitive skin with broad-spectrum protection.", 18.00, 29.99, "Beauty & Wellness"));
        products.add(new Product("Foreo Luna Mini 2", "Silicone facial cleansing brush with T-Sonic pulsations.", 80.00, 119.00, "Beauty & Wellness"));
        products.add(new Product("Bio-Oil Skincare Oil", "Multipurpose oil that improves the appearance of scars and stretch marks.", 10.00, 19.99, "Beauty & Wellness"));

        // More Toys & Games
        products.add(new Product("Melissa & Doug Wooden Train Set", "Classic wooden train tracks and accessories for imaginative play.", 20.00, 39.99,"Beauty & Wellness"));
        products.add(new Product("Hot Wheels 50-Car Pack", "Collection of 1:64 scale Hot Wheels cars for racing and collecting.", 35.00, 59.99,"Beauty & Wellness"));
        products.add(new Product("Play-Doh 36-Can Mega Pack", "Colorful modeling compound for creative play and learning.", 12.00, 27.99,"Beauty & Wellness"));
        products.add(new Product("NERF Elite 2.0 Blaster", "Foam dart blaster with quick reload and long-range firing.", 20.00, 39.99,"Beauty & Wellness"));
        products.add(new Product("Fisher-Price Laugh & Learn Smart Stages Chair", "Interactive toddler chair that teaches shapes, colors, and numbers.", 25.00, 49.99,"Beauty & Wellness"));
        products.add(new Product("Board Game: Catan", "Popular strategy board game involving resource management and trading.", 25.00, 44.99,"Beauty & Wellness"));
        products.add(new Product("Barbie Dreamhouse Dollhouse", "Three-story dollhouse with lights, sounds, and multiple accessories.", 130.00, 199.99,"Beauty & Wellness"));
        products.add(new Product("Nintendo Switch Pro Controller", "High-performance wireless controller for the Nintendo Switch.", 50.00, 69.99,"Beauty & Wellness"));


        return products;
    }

}
