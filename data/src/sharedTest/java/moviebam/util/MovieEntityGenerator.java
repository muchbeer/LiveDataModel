package moviebam.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

import muchbeer.raum.com.data.model.Movie;

public class MovieEntityGenerator {

    private final static Random rand = new Random();
    private static int id = 0;

    public static Movie createRandomEntity() {
        Movie entity = new Movie();
        entity.setTitle("It Chapter Two");
        entity.setOverview("Peter Parker and his friends go on a summer trip to Europe. However, they will hardly be able to rest - Peter will have to agree to help Nick Fury uncover the mystery of creatures that cause natural disasters and destruction throughout the continent.");
        entity.setReleaseDate("2019-10-04");
        entity.setVoteAverage(rand.nextDouble());
        entity.setPosterPath("http:google.com/zfE0R94v1E8cuKAerbskfD3VfUt.jpg");


        return entity;
    }


}
