package graph;

import java.util.concurrent.atomic.AtomicInteger;

public class Identifier implements Identifiable {
    static final AtomicInteger NEXT_ID = new AtomicInteger(0);
    final int id = NEXT_ID.getAndIncrement(); //get a new id when the new object initializes its fields. Atomic ensures safety in concurrency

    @Override
    public Integer getId() {
        return id;
    }
}
