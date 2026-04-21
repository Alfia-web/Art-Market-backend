//
//
//import com.example.demo.entity.Genres;
//import com.example.demo.repository.ImageRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.example.demo.entity.Image;
//import java.util.List;
//
//@
//class LoadDatabase {
//
//    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
//
//    @Bean //автмоатически создаёт объект и код выполняется при запуске приложения
//    CommandLineRunner initDatabase(ImageRepository repository) {
//        return args -> {
////            repository.deleteAll();
////
////            repository.save(new Image("На озере", "Неизвестный", "/images/in_the_lake.jpg", 600, 732, Genres.Без_жанра));
////            repository.save(new Image("Три музы", "Неизвестный", "/images/three_muses.jpg", 527, 740, Genres.Без_жанра));
////            repository.save(new Image("В окне", "Неизвестный", "/images/in_the_window.jpg", 736, 981, Genres.Без_жанра));
////            repository.save(new Image("Рыбки", "Неизвестный", "/images/green_fish.jpg", 750, 750, Genres.Без_жанра));
////            repository.save(new Image("Девочка на море", "Неизвестный", "/images/girl_on_the_sea.jpg", 600, 750, Genres.Без_жанра));
////            repository.save(new Image("Пианистка", "Неизвестный", "/images/piano_player.jpg", 1200, 1487, Genres.Без_жанра));
////            repository.save(new Image("Дама с книгой", "Неизвестный", "/images/dama_with_book.jpg", 596, 800, Genres.Без_жанра));
////            repository.save(new Image("Утёное", "Неизвестный", "/images/duckling.jpg", 736, 736, Genres.Без_жанра));
////            repository.save(new Image("Утро", "Неизвестный", "/images/morning.jpg", 736, 736, Genres.Без_жанра));
////            repository.save(new Image("Весна", "Неизвестный", "/images/spring.jpg", 1200, 1536, Genres.Без_жанра));
////            repository.save(new Image("Чай", "Неизвестный", "/images/tea.jpg", 500, 468, Genres.Без_жанра));
//
//            List<Image> images = repository.findAll();
//
//            for(Image img: images){
//                log.info("Loaded: " + img);
//            }
//        };
//    }
//}