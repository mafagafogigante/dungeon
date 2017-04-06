package org.mafagafogigante.dungeon.io;

import com.eclipsesource.json.JsonObject;

class ResourcesTypeTest {

  static JsonObject getJsonObjectByJsonFile(JsonFileName jsonFileName) {
    return JsonObjectFactory.makeJsonObject(jsonFileName.getStringRepresentation());
  }

  enum JsonFileName {

    WIKI("wiki.json"), HINTS("hints.json"), POEMS("poems.json"), ITEMS("items.json"), DREAMS("dreams.json"),
    PREFACE("preface.json"), TUTORIAL("tutorial.json"), LOCATIONS("locations.json"), CREATURES("creatures.json"),
    ACHIEVEMENTS("achievements.json");

    private final String stringRepresentation;

    JsonFileName(String stringRepresentation) {
      this.stringRepresentation = stringRepresentation;
    }

    public String getStringRepresentation() {
      return stringRepresentation;
    }

    @Override
    public String toString() {
      return stringRepresentation;
    }

  }

}
