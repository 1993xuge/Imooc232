package com.xuge.imooc232.test;

public class MenuTestDrive {
    public static final void main(String[] args) {
//        MenuComponent pancakeHouseMenu = new Menu("PANCAKE HOUSE MENU", "Breakfast");
//        MenuComponent dinerMenu = new Menu("DINER MENU", "Lunch");
//        MenuComponent cafeMenu = new Menu("CAFE MENU", "Dinner");
//        MenuComponent dessertMenu = new Menu("DESSERT MENU", "Dessert Of Course!");
//
//        MenuComponent allMenus = new Menu("All MENUS", "All Menus combined");
//
//        allMenus.add(pancakeHouseMenu);
//        allMenus.add(dinerMenu);
//        allMenus.add(cafeMenu);
//
//        dinerMenu.add(new MenuItem("Pasta", "Pasta MenuItem", true, 3.89));
//        dinerMenu.add(dessertMenu);


//        Waitress waitress = new Waitress(allMenus);
//        waitress.printMenu();

        Menu menuA = new Menu("A", "A");
        Menu menuB = new Menu("B", "B");
        Menu menuC = new Menu("C", "C");

        MenuItem menuItemD = new MenuItem("D", "D", false, 1.2);
        MenuItem menuItemE = new MenuItem("E", "E", false, 1.2);
        MenuItem menuItemF = new MenuItem("F", "F", false, 1.2);

        menuA.add(menuB);
        menuA.add(menuC);

        menuB.add(menuItemD);
        menuB.add(menuItemE);

        menuC.add(menuItemF);

        System.out.println(menuA.createIterator().next());
    }
}
