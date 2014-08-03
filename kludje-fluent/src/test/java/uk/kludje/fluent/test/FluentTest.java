package uk.kludje.fluent.test;

import org.junit.Assert;
import org.junit.Test;
import uk.kludje.fn.function.UConsumer;

import static uk.kludje.fluent.Fluent.fluent;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class FluentTest {

  @Test
  public void testInvoke() {
    AtomicInteger i = fluent(new AtomicInteger())
        .f(AtomicInteger::incrementAndGet)
        .f(AtomicInteger::incrementAndGet)
        .get();
    Assert.assertEquals(2, i.get());
  }

  @Test(expected=IOException.class)
  public void testExceptions() {
    Consumer<FluentTest> ti = (UConsumer<FluentTest>) FluentTest::throwIt;
    fluent(this).f(ti);
  }

  private void throwIt() throws IOException {
    throw new IOException();
  }

  @Test
  public void testListPopulation() {
    List<String> list = fluent(new ArrayList<String>())
        .f(List::add, "a")
        .f(List::add, "b")
        .f(List::add, "c")
        .f(List::remove, "b")
        .map(Collections::unmodifiableList)
        .get();

    Assert.assertEquals(2, list.size());
  }

  @Test
  public void testMapPopulation() {
    Map<String, String> map = fluent(new HashMap<String, String>())
        .f(Map::put, "a", "A")
        .f(Map::put, "b", "B")
        .f(Map::put, "c", "C")
        .map(Collections::unmodifiableMap)
        .get();

    Assert.assertEquals(2, map.size());
  }
}