package exe201.flexora.service.Impl;

import exe201.flexora.dto.GameDTO;
import exe201.flexora.entity.Game;
import exe201.flexora.mapper.GameMapper;
import exe201.flexora.repository.GameRepository;
import exe201.flexora.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class GameServiceImpl  implements GameService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GameMapper gameMapper;
    @Override
    @Transactional
    public GameDTO createGame(GameDTO dto) {
        // Kiểm tra xem tên game đã tồn tại chưa
        if (gameRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalArgumentException("Game với tên '" + dto.getName() + "' đã tồn tại!");
        }

        Game game = gameMapper.toEntity(dto);
        game = gameRepository.save(game);
        return gameMapper.toDto(game);
    }
}
