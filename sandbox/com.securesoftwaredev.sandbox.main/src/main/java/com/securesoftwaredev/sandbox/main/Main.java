package com.securesoftwaredev.sandbox.main;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;



/**
 * The main program loads {@linkplain Plugin plugins} through the Java
 * <a href="http://docs.oracle.com/javase/7/docs/api/java/util/ServiceLoader.html">Service Provider Interface</a>
 * and runs them. Some of these plugins may be mobile code, so we're relying on externally set permissions.
 */
public class Main {

  private ThreadGroup pluginGroup;
  private CountDownLatch doneSignal;

  public static void main(String[] args) {
    new Main().run();
  }

  private void run() {
    System.out.println("\nStarting main program");
    startPlugins();
    System.out.println("Running plugins\n");
    waitForPluginsToFinish();
    System.out.println("\nDone\n");
  }

  private void waitForPluginsToFinish() {
    try {
      doneSignal.await(1, TimeUnit.MINUTES);
    } catch (InterruptedException e) {
      System.out.println("!!! Plugins didn't finish in time; aborting");
    }
  }

  private void startPlugins() {
    ServiceLoader<Plugin> pluginLoader = ServiceLoader.load(Plugin.class);
    prepare(pluginLoader.iterator());
    runPlugins(pluginLoader.iterator());
  }

  private void prepare(Iterator<Plugin> plugins) {
    pluginGroup = new ThreadGroup("Plugins");
    doneSignal = new CountDownLatch(getNumPlugins(plugins));
  }

  private int getNumPlugins(Iterator<Plugin> plugins) {
    int result = 0;
    while (plugins.hasNext()) {
      result++;
      plugins.next();
    }
    return result;
  }

  private void runPlugins(Iterator<Plugin> plugins) {
    while (plugins.hasNext()) {
      runPlugin(plugins.next());
    }
  }

  private void runPlugin(final Plugin plugin) {
    new Thread(pluginGroup, new Runnable() {
      @Override
      public void run() {
        try {
          plugin.run();
        } catch (Throwable t) {
          System.out.println("=> Error in plugin: " + t.getMessage());
        }
        doneSignal.countDown();
      }
    }, plugin.getClass().getName()).start();
  }

}
