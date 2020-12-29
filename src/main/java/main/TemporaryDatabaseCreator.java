//package main;
//
//import DB.entities.Country;
//import DB.entities.OWID;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Date;
//
//public class TemporaryDatabaseCreator {
//
//   // private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//
//    public static void main(String[] args) throws ParseException {
//        EntityManagerFactory factory= Persistence.createEntityManagerFactory("mysql_local");
//        EntityManager manager=factory.createEntityManager();
//
//        System.out.println("Connection to MySQL is open?: " + manager.isOpen());
//
//
//        Date date20201101 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-01");
//        Date date20201102 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-02");
//        Date date20201103 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-03");
//        Date date20201104 = new SimpleDateFormat("yyyy-MM-dd").parse("2020-11-04");
//
//
//
//
//        Country poland=new Country("POL","Europe","Poland",38000000);
//        Country usa=new Country("USA","North America","USA",330000000);
//        Country australia=new Country("AUS","Australia","Australia",32000000);
//
//        OWID pol20201101=new OWID(poland,date20201101,37990,17171,5783,152,
//                null,152,4585135,48341);
//        OWID pol20201102=new OWID(poland,date20201102,395480,15578,5875,92,
//                null,17223,4649236,64101);
//        OWID pol20201103=new OWID(poland,date20201103,414844,19364,6102,227,
//                null,18160,4712224,62988);
//        OWID pol20201104=new OWID(poland,date20201104,439536,24692,6475,373,
//                null,18654,4779914,67690);
//        OWID usa20201101=new OWID(usa,date20201101,9241521,104327,231623,422,
//                9665,47615,153426532,877936);
//        OWID usa20201102=new OWID(usa,date20201102,9324616,83095,232155,532,
//                9970, 48773,154409790,983258);
//        OWID usa20201103=new OWID(usa,date20201103,9450988,126372,233720,1565,
//                10530,50512,155728586,1318796);
//        OWID usa20201104=new OWID(usa,date20201104,9554518,103530,234812,1092,
//                10892, 52166,157298430,1569844);
//        OWID australia20201101=new OWID(australia,date20201101,27601,6,907,0,
//                null,null,8825186,null);
//        OWID australia20201102=new OWID(australia,date20201102,27610,9,907,0,
//                null,null,8855401,30215);
//        OWID australia20201103=new OWID(australia,date20201103,27622,12,907,0,
//                null,null,8887171,31770);
//        OWID australia20201104=new OWID(australia,date20201104,27630,8,907,0,
//                null,null,8933563,46392);
//
//
//        manager.getTransaction().begin();
//manager.persist(poland);
//manager.persist(usa);
//manager.persist(australia);
//manager.persist(pol20201101);
//manager.persist(pol20201102);
//manager.persist(pol20201103);
//manager.persist(pol20201104);
//manager.persist(usa20201101);
//manager.persist(usa20201102);
//manager.persist(usa20201103);
//manager.persist(usa20201104);
//manager.persist(australia20201101);
//manager.persist(australia20201102);
//manager.persist(australia20201103);
//manager.persist(australia20201104);
//manager.getTransaction().commit();
//
//
//manager.close();
//factory.close();
//
//
//
//    }
//}
