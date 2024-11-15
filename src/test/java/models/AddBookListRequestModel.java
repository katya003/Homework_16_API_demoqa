package models;

import lombok.Data;

import java.util.List;

@Data
public class AddBookListRequestModel {
    String userId;
    List<IsbnModel> collectionOfIsbns;
}
