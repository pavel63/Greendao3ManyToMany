# Greendao3ManyToMany
Greendao3 many-to-many:
many-to-many-”многое-ко-многим”-отношения,когда любая запись в одной таблице может ссылаться на любую запись в другой таблице.Также и записи в другой таблице могут ссылаться на несколько записей в другой таблице.Как например один завод по производству соков может делать оные с несколькими вкусами и соки могут иметь несколько вкусов.Или разные люди могут слать письма разным людям.Мы ставим id в спец колонку у каждого и в случае “совпадения” между id означает,что между ними установлена связь.Список связей у нас будет в третьей таблице-id к id


Создадим три таблицы-Product с названиями продуктов,Order с заказами и третью таблицу,их соединяющую.

стандартно создаем проект(в предыдущих конспектах разбиралось).Но теперь наши классы-таблицы будут выглядеть иначе-
мы должны добавить перекрестные ссылки путем аннотаций Greendao @ToMany и @JoinEntity.Также указываем наш третий класс-соединительной таблицы:


@Entity
public class Product {
    @Id
    private Long id;

    private String name;

//В Product:
    @ToMany
    @JoinEntity(
            entity = JoinProductsWithOrders.class,
            sourceProperty = "productId",
            targetProperty = "orderId"
    )
    private List<Order> ordersWithThisProduct;
}



@Entity
public class Order {
    @Id
    private Long id;

    private String name;
//И в Order:
    @ToMany
    @JoinEntity(
            entity = JoinProductsWithOrders.class,
            sourceProperty = "orderId",
            targetProperty = "productId"
    )
    private List<Product> productsForThisOrder;
}
//А и создать колонки  с сылками в третьей таблице:

@Entity
public class JoinProductsWithOrders {
    @Id
    private Long id;
    private Long productId;
    private Long orderId;
}


далее как обычно создаем объекты классов и вставляем их в Дао(в предидущих конспектах разбиралось)
А теперь создаем объекты класса JoinProductsWithOrders и устанавливаем необходимые нам значения:

//пишем какой продукт в каком заказе(2 параметр-id продукта,3-й-id заказа)
        //не забываем что отсчет в таблице идет с 1
        JoinProductsWithOrders joinProductsWithOrders=new JoinProductsWithOrders(null,1L,1L);
        JoinProductsWithOrders joinProductsWithOrders2=new JoinProductsWithOrders(null,2L,1L);
        JoinProductsWithOrders joinProductsWithOrders3=new JoinProductsWithOrders(null,3L,1L);
        JoinProductsWithOrders joinProductsWithOrders4=new JoinProductsWithOrders(null,3L,2L);
        JoinProductsWithOrders joinProductsWithOrders5=new JoinProductsWithOrders(null,2L,2L);
        JoinProductsWithOrders joinProductsWithOrders6=new JoinProductsWithOrders(null,1L,3L);


далее стандартным запросом получаем например запись о втором заказе

//и одной строчкой берем список продуктов для этого заказа:
        List<Product>products=sec.getProductsForThisOrder();

Угадаем,что получим;смотрим на нашу таблицу:




Правильно,в данном заказе продукты 2 и 3

ну или наоборот-нам нужно узнать,в каких заказах встречается например продукт 1

зеркально запрашиваем: List<Order>ordersList=product1.getOrdersWithThisProduct();

смотрим,что же должен выдасть:


правильно,продукт номер 1 у нас в заказах 1 и 3

