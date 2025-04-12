package com.example.demo.service;


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
        generateProducts();
        generateInventoryStocks();
        generateSuppliers();
        generateOrders();
        generateSales();
    }

    private void generateUsers(){
        List<String> names = List.of("Oleg", "Maxime", "Regina", "Klaus", "Zon", "Nico", "Luka", "Inigo", "Sutja");
        List<String> emails = new ArrayList<String>();
        List<AuthRequest> requests = new ArrayList<AuthRequest>();
        for (int i = 0; i < names.size(); i++) {
            String email = names.get(i) + "@cogent.tech";
            emails.add(email);
            AuthRequest request = new AuthRequest();
            request.setUsername(email);
            request.setPassword("password");
            Role role = Role.values()[new Random().nextInt(Role.values().length)];
            request.setRole(role);
            requests.add(request);
            AuthResponse response = userAuthClient.register(request);
            if (response.isSuccessful()) {
                users.add(request);
            }
        }
    }

    private void generateProducts(){
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
            product.setSku(new Random().nextInt());
            String adjective = adjectives.get(new Random().nextInt(adjectives.size()));
            String noun = nouns.get(new Random().nextInt(nouns.size()));
            product.setName(adjective + " " + noun);
            product.setDescription(String.format(descriptionFormats.get(new Random().nextInt(descriptionFormats.size())), adjective, noun));
            product.setBuyPrice(new Random().nextDouble(10, 100));
            product.setSellPrice(new Random().nextDouble(10, 100) + product.getBuyPrice());
            product.setInitial_stock(new Random().nextInt(1, 15));
            product = productRepository.save(product);
            products.add(product);
        }
    }
    private void generateInventoryStocks() {
        for (ProductEntity product : products) {
            InventoryStockEntity inventoryStock = new InventoryStockEntity();
            inventoryStock.setSku(product.getSku());
            inventoryStock.setQuantity(product.getInitial_stock());
            inventoryStock = inventoryStockRepository.save(inventoryStock);
            inventoryStocks.add(inventoryStock);
        }
    }

    private void generateSuppliers() {
        List<String> names = List.of("Supplier A", "Supplier B", "Supplier C", "Supplier D", "Supplier E");
        List<String> addresses = List.of("123 Main St", "456 Elm St", "789 Oak St", "101 Pine St", "202 Maple St");
        List<String> phones = List.of("1234567890", "0987654321", "5555555555", "1112223333", "4445556666");
        List<String> contacts = List.of("John Doe", "Jane Smith", "Alice Johnson", "Bob Brown", "Charlie Davis");
        List<String> contactInfos = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            String contactInfoString = contacts.get(new Random().nextInt(contacts.size())) + "\n" + phones.get(new Random().nextInt(phones.size())) + "\n" + addresses.get(new Random().nextInt(addresses.size()));
            contactInfos.add(contactInfoString);
        }
        for (int i = 0; i < 10; i++) {
            SupplierEntity supplier = new SupplierEntity();
            supplier.setName(names.get(new Random().nextInt(names.size())));
            supplier.setContactInfo(contactInfos.get(new Random().nextInt(contactInfos.size())));
            supplier.setRating(String.valueOf(new Random().nextDouble(1, 5)));
            supplier = supplierRepository.save(supplier);
            suppliers.add(supplier);
        }
    }

    private void generateOrders() {
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            order.setSupplierId(new Random().nextInt(1, 10));
//            order.setTotalPrice(new Random().nextDouble(100, 1000));
            order.setOrderStatus(OrderStatus.PENDING);
            List<OrderProduct> orderProductList = generateOrderProducts();
            order.setTotalPrice(orderProductList.stream().mapToDouble(op -> op.getPrice() * op.getQuantity()).sum());
            order.setProducts(orderProductList);
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
            orderProductList.add(orderProduct);
        }
        return orderProductList;
    }

    private void generateSales() {
        for (int i = 0; i < 10; i++) {
            Sale sale = new Sale();
//            sale.setTotalPrice(new Random().nextDouble(100, 1000));
            sale.setSaleStatus(SaleStatus.PENDING);
            List<String> userRoleUsernames = users.stream().filter(user -> user.getRole() == Role.USER).map(user-> user.getUsername()).toList();
            String randomUsername = userRoleUsernames.get(new Random().nextInt(userRoleUsernames.size()));
            sale.setCustomerUsername(randomUsername);
            List<SaleProduct> saleProductList = generateSaleProducts();
            sale.setTotalPrice(saleProductList.stream().mapToDouble(sp -> sp.getPrice() * sp.getQuantity()).sum());
            sale.setProducts(saleProductList);
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
            saleProductList.add(saleProduct);
        }
        return saleProductList;
    }

}
