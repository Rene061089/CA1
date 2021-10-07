package facades;

import dtos.HobbiesDTO;
import dtos.HobbyDTO;

public interface IHobbyFacade {

    HobbyDTO addHobby (HobbyDTO hdto);
    HobbiesDTO getAllHobbies ();
    HobbyDTO removeHobby (int id);
    HobbyDTO updateHobby (HobbyDTO hdto);

}
