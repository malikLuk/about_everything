package multithreading.youtube;

import java.util.ArrayList;
import java.util.List;


/**
 * Типа браузер
 * */
class Browser {

    List<Page> pages = new ArrayList<>();

    synchronized Page openPage() {
        Page page = new Page(this);
        pages.add(page);
        return page;
    }

    /**
     * так то equals и hashCode не переопределены
     * поэтому хуйня. Но схематично сойдет
     * */
    synchronized void removePage(Page page) {
        pages.remove(page);
    }

    /**
     * Закрыть браузер
     * */
    synchronized void close() {
        pages.forEach(Page::close);
    }

}

/**
 * Страница в браузере
 * */
class Page {

    private Browser browser;

    Page(Browser browser) {
        this.browser = browser;
    }

    synchronized void close() {
        browser.removePage(this);
    }

}

public class DeadLock {

    public static void main(String[] args) {
        Browser browser = new Browser();

        for (int i = 0; i < 10; i++) {
            System.out.println("page " + i);
            Page openPage = browser.openPage();
            new Thread(() -> {
                //...
                openPage.close();
            }).start();
        }

        browser.close();
        System.out.println("finished");
    }

}
