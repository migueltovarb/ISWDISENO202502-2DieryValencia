public class Main {

    interface Starter { String getDescription(); }
    interface MainCourse { String getDescription(); }
    interface Drink { String getDescription(); }
    interface Dessert { String getDescription(); }

    interface MenuFactory {
        Starter createStarter();
        MainCourse createMainCourse();
        Drink createDrink();
        Dessert createDessert();
    }

    static class ItalianMenuFactory implements MenuFactory {
        public Starter createStarter() { return () -> "Bruschetta con tomate y albahaca"; }
        public MainCourse createMainCourse() { return () -> "Lasaña a la boloñesa"; }
        public Drink createDrink() { return () -> "Vino tinto Chianti"; }
        public Dessert createDessert() { return () -> "Tiramisú clásico"; }
    }

    static class JapaneseMenuFactory implements MenuFactory {
        public Starter createStarter() { return () -> "Edamame al vapor"; }
        public MainCourse createMainCourse() { return () -> "Sushi variado (nigiri, maki)"; }
        public Drink createDrink() { return () -> "Té verde japonés"; }
        public Dessert createDessert() { return () -> "Mochi de té matcha"; }
    }

    static class MexicanMenuFactory implements MenuFactory {
        public Starter createStarter() { return () -> "Totopos con guacamole"; }
        public MainCourse createMainCourse() { return () -> "Tacos al pastor"; }
        public Drink createDrink() { return () -> "Agua fresca de horchata"; }
        public Dessert createDessert() { return () -> "Churros con chocolate"; }
    }

    static class Restaurant {
        private MenuFactory menuFactory;

        public Restaurant(MenuFactory menuFactory) {
            this.menuFactory = menuFactory;
        }

        public void showMenu() {
            System.out.println("=== MENÚ DEL DÍA ===");
            System.out.println("Entrada: " + menuFactory.createStarter().getDescription());
            System.out.println("Plato fuerte: " + menuFactory.createMainCourse().getDescription());
            System.out.println("Bebida: " + menuFactory.createDrink().getDescription());
            System.out.println("Postre: " + menuFactory.createDessert().getDescription());
        }
    }

    public static void main(String[] args) {
        Restaurant italian = new Restaurant(new ItalianMenuFactory());
        italian.showMenu();

        System.out.println();

        Restaurant japanese = new Restaurant(new JapaneseMenuFactory());
        japanese.showMenu();

        System.out.println();

        Restaurant mexican = new Restaurant(new MexicanMenuFactory());
        mexican.showMenu();
    }
}
