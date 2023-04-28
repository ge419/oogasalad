package oogasalad.model.constructable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.checkerframework.checker.units.qual.A;

public class Players {
  private List<Player> players;

  public Players() {
    this.players = new ArrayList<>();
  }

  @JsonCreator
  public Players(@JsonProperty("players") List<Player> p) {
    this.players = new ArrayList<>(p);
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  @JsonIgnore
  public Optional<Player> getById(String id) {
    for (Player p : players) {
      if (p.getId().equals(id)) {
        return Optional.of(p);
      }
    }

    return Optional.empty();
  }

  @JsonIgnore
  public Optional<Piece> getPieceById(String id) {
    for (Player player : players) {
      for (Piece p : player.getPieces()) {
        if (p.getId().equals(id)) {
          return Optional.of(p);
        }
      }
    }

    return Optional.empty();
  }

  @JsonIgnore
  public void randomize() throws IOException {

    ObjectMapper objectMapper = new ObjectMapper();
    File jsonFile = new File("src/main/resources/view.gameplay/DefaultPlayers.json");
    JsonNode rootNode = objectMapper.readTree(jsonFile);
    List<String> colors = new ArrayList<>();
    for (JsonNode colorNode : rootNode.get("colors")) {
      colors.add(colorNode.asText());
    }
    List<String> icons = new ArrayList<>();
    for (JsonNode iconNode : rootNode.get("icons")) {
      icons.add(iconNode.asText());
    }
    for (int i = 0; i < players.size(); i++) {
      Random random = new Random();
      try {
        String randomColor = colors.remove(random.nextInt(colors.size()));
        String randomIcon = icons.remove(random.nextInt(icons.size()));
        players.get(i).setColor(randomColor);
        players.get(i).setImage(randomIcon);
        players.get(i).setName("Player " + i);
      } catch (Exception e) {
        throw new RuntimeException(e + "Maximum of 8 players allowed");
      }

    }
  }
}
