package exe201.flexora.service;

import exe201.flexora.dto.RoomDTO;
import exe201.flexora.dto.ShowRoomDTO;

import java.util.List;

public interface RoomService {
    ShowRoomDTO createRoom(RoomDTO roomDTO, Long clubId);
    ShowRoomDTO getRoomById(Long roomId);
    RoomDTO updateRoom(Long roomId, RoomDTO roomDTO);
    void deleteRoom(Long roomId);
    List<ShowRoomDTO> getAllRooms();
}
