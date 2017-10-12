package com.example.android_razrab.greendaomanytomany;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MainActivity extends AppCompatActivity {

    DaoSession mDaoSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDaoSession=((App)getApplication()).getDaoSession();

        OrderDao orderDao=mDaoSession.getOrderDao();
        ProductDao productDao=mDaoSession.getProductDao();
        JoinProductsWithOrdersDao joinProductsWithOrdersDao=mDaoSession.getJoinProductsWithOrdersDao();


        //создаем продукты
        Product product=new Product(null,"Chicken");
        Product product2=new Product(null,"Apple");
        Product product3=new Product(null,"Caviar");
        Product product4=new Product(null,"Salt");

        productDao.insert(product);
        productDao.insert(product2);
        productDao.insert(product3);
        productDao.insert(product4);


        //создаем заказы
        Order order=new Order(null,"First");
        Order order2=new Order(null,"Second");
        Order order3=new Order(null,"Third");

        orderDao.insert(order);
        orderDao.insert(order2);
        orderDao.insert(order3);


        //пишем какой продукт в каком заказе(2 параметр-id продукта,3-й-id заказа)
        //не забываем что отсчет в таблице идет с 1
        JoinProductsWithOrders joinProductsWithOrders=new JoinProductsWithOrders(null,1L,1L);
        JoinProductsWithOrders joinProductsWithOrders2=new JoinProductsWithOrders(null,2L,1L);
        JoinProductsWithOrders joinProductsWithOrders3=new JoinProductsWithOrders(null,3L,1L);
        JoinProductsWithOrders joinProductsWithOrders4=new JoinProductsWithOrders(null,3L,2L);
        JoinProductsWithOrders joinProductsWithOrders5=new JoinProductsWithOrders(null,2L,2L);
        JoinProductsWithOrders joinProductsWithOrders6=new JoinProductsWithOrders(null,1L,3L);

        joinProductsWithOrdersDao.insert(joinProductsWithOrders);
        joinProductsWithOrdersDao.insert(joinProductsWithOrders2);
        joinProductsWithOrdersDao.insert(joinProductsWithOrders3);
        joinProductsWithOrdersDao.insert(joinProductsWithOrders4);
        joinProductsWithOrdersDao.insert(joinProductsWithOrders5);
        joinProductsWithOrdersDao.insert(joinProductsWithOrders6);


        QueryBuilder<Order>orderQueryBuilder=orderDao.queryBuilder()
                .where(OrderDao.Properties.Name.eq("Second"));
        orderQueryBuilder.build();
        List<Order>orders=orderQueryBuilder.list();

        Order sec=orders.get(0);

        //берем список продуктов для этого заказа:
        List<Product>products=sec.getProductsForThisOrder();

        for (int i=0;i<products.size();i++){

            Log.d("PRODUCTS_IN_THIS_ORD: ",products.get(i).getName());

        }

        //и видим,что во втором заказе у нас икра и яблоки



        //или смотрим в каких заказах данный продукт присутствует:

        QueryBuilder<Product>productQueryBuilder=productDao.queryBuilder()
                .where(ProductDao.Properties.Name.eq("Chicken"));
        productQueryBuilder.build();

        List<Product>products1=productQueryBuilder.list();

        Product product1=products1.get(0);

        List<Order>ordersList=product1.getOrdersWithThisProduct();

        for(int i=0;i<ordersList.size();i++){

            Log.d("THIS_PRODUCT_IN_ORDERS",ordersList.get(i).getName());
        }

        //и видим,что цыленок у нас есть в первом и третьем заказах

    }
}
