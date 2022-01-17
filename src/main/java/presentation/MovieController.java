package presentation;

import domain.Movie;
import domain.RestConsumer;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MovieController {

    @RequestMapping(value= "/movie/by-title", method = RequestMethod.POST)
    public List<Movie> listMoviesByDirector(@RequestParam(name = "title") String title){
        List<Movie> movies=null;
        try{
            movies = RestConsumer.getInstance().listMoviesByTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }
}
