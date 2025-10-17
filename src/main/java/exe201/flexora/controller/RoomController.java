package exe201.flexora.controller;

import exe201.flexora.dto.RoomDTO;
import exe201.flexora.dto.ShowRoomDTO;
import exe201.flexora.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/{clubId}")
    public ShowRoomDTO createRoom(@RequestBody RoomDTO roomDTO, @PathVariable Long clubId){
        return roomService.createRoom(roomDTO, clubId);
    }
    @GetMapping("/{roomId}")
    public ShowRoomDTO getRoomById(@PathVariable Long roomId){
        return roomService.getRoomById(roomId);
    }
    @PatchMapping("/{roomId}")
    public RoomDTO updateRoom(@PathVariable Long roomId,@RequestBody RoomDTO roomDTO){
        return roomService.updateRoom(roomId, roomDTO);
    }
    @DeleteMapping("/{roomId}")
    public void deleteRoom(@PathVariable Long roomId){
        roomService.deleteRoom(roomId);
    }
    @GetMapping
    public List<ShowRoomDTO> getAllRooms(){
        return roomService.getAllRooms();
    }
}
