package com.example.duper.dupes;

public interface DupeMethod {
    void activate();   // start the dupe loop (usually in a new thread)
    void deactivate(); // stop the dupe loop
}
