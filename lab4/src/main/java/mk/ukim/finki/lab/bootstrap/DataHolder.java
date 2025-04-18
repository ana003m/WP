package mk.ukim.finki.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.lab.model.*;
import mk.ukim.finki.lab.repository.jpa.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import mk.ukim.finki.lab.model.enumerations.Role;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Artist> artistList = null;
    public static List<Song> songsList = null;
    public static List<Album> albumList = null;

    public static List<User> userList=null;




    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public DataHolder(AlbumRepository albumRepository, SongRepository songRepository, ArtistRepository artistRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @PostConstruct
    public void init() {
        artistList = new ArrayList<>();
        if (artistRepository.count() == 0) {
            artistList.add(new Artist("Ariana", "Grande", "Artist1_Bio"));
            artistList.add(new Artist("Bruno", "Mars", "Artist2_Bio"));
            artistList.add(new Artist("Billie", "Eilish", "Artist3_Bio"));
            artistList.add(new Artist("Dua", "Lipa", "Artist4_Bio"));
            artistList.add(new Artist("Harry", "Styles", "Artist5_Bio"));
            this.artistRepository.saveAll(artistList);
        }



        albumList = new ArrayList<>();
        if (this.albumRepository.count() == 0) {
            albumList.add(new Album("Imagine Dragons", "Pop", "2024"));
            albumList.add(new Album("Miles Davis", "Jazz", "1997"));
            albumList.add(new Album("Fleetwood Mac", "Pop-rock", "1989"));
            albumList.add(new Album("Eagles", "Rock", "2003"));
            albumList.add(new Album("The Beatles", "Pop", "1969"));
            this.albumRepository.saveAll(albumList);
        }

        songsList = new ArrayList<>();
        if (this.songRepository.count() == 0) {
            songsList.add(new Song("Heavenly Touch", "Pop", 2024, new ArrayList<>(), albumList.get(0)));
            songsList.add(new Song("Lost Horizons", "Soul", 2024, new ArrayList<>(), albumList.get(1)));
            songsList.add(new Song("Whispered Dreams", "Acoustic", 2024, new ArrayList<>(), albumList.get(2)));
            songsList.add(new Song("Eternal Flame", "Indie Pop", 2024, new ArrayList<>(), albumList.get(3)));
            songsList.add(new Song("Stardust Memories", "Chillwave", 2024, new ArrayList<>(), albumList.get(4)));
            this.songRepository.saveAll(songsList);
        }



        userList = new ArrayList<>();
        if (this.userRepository.count() == 0){
            userList.add(new User("admin", passwordEncoder.encode("admin"),"admin","admin", Role.ROLE_ADMIN ));
            userList.add(new User("user", passwordEncoder.encode("user"),"user","user", Role.ROLE_USER ));
            this.userRepository.saveAll(userList);
        }
    }
}
