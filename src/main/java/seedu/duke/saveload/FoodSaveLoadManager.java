package seedu.duke.saveload;

import seedu.duke.food.Food;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Server as a in-between class between loader saver and food list
 */
public class FoodSaveLoadManager {
    private static final int DEFAULT_SAVER_WIDTH = 5;
    private static final int DEFAULT_SAVER_HEIGHT = 10;

    private static final String FOOD_FOLDER_NAME = "Food%%UOISDN%%FOLDER";

    private static final String DEFAULT_NAME = "MISSING NAME";
    private static final String DEFAULT_NUTRITION_VALUE = "0";

    private Saver saver;
    private Loader fileLoader;

    public FoodSaveLoadManager() {
        this.saver = new Saver(DEFAULT_SAVER_WIDTH, DEFAULT_SAVER_HEIGHT);
        this.fileLoader = Loader.loadEmpty();
    }

    /**
     * Call this function to load a food file
     * @param fileName name of file
     * @throws FileNotFoundException if there is no such save file
     */
    public void load(String fileName) throws FileNotFoundException {
        this.fileLoader = Loader.load(FOOD_FOLDER_NAME, fileName);
    }

    public void clearLoader(){
        this.fileLoader = Loader.loadEmpty();
    }

    /**
     * Returns a list of food that is stored in the loader
     * @return
     * @throws IllegalAccessException When this method is called without first loading a food file
     */
    public List<Food> getFoodList() throws IllegalAccessException {
        ArrayList<Food> foodlist = new ArrayList<>();
        Food newFood;
        for (int j = 1; j < fileLoader.getHeight() + 1; j++) {
            newFood = new Food(
                    fileLoader.get(j,1).orElse(DEFAULT_NAME),
                    Integer.parseInt(fileLoader.get(j,2).orElse(DEFAULT_NUTRITION_VALUE)),
                    Integer.parseInt(fileLoader.get(j,3).orElse(DEFAULT_NUTRITION_VALUE)),
                    Integer.parseInt(fileLoader.get(j,4).orElse(DEFAULT_NUTRITION_VALUE)),
                    Integer.parseInt(fileLoader.get(j,5).orElse(DEFAULT_NUTRITION_VALUE)));
            foodlist.add(newFood);
        }
        return foodlist;
    }

    /**
     * saves a input food list to a file
     * @param fileName the name of the file to save to
     * @param foodlist list of food objects to be saved
     */
    public void save(String fileName, List<Food> foodlist){
        this.saver.resetSize(DEFAULT_SAVER_WIDTH, foodlist.size());
        for (int j = 1; j < foodlist.size() + 1; j++){
            saver.add(foodlist.get(j-1).getName(), 1, j);
            saver.add(Integer.toString(foodlist.get(j-1).getCalorie()), 2, j);
            saver.add(Integer.toString(foodlist.get(j-1).getCarbohydrate()), 3, j);
            saver.add(Integer.toString(foodlist.get(j-1).getProtein()), 4, j);
            saver.add(Integer.toString(foodlist.get(j-1).getFats()), 5, j);
        }
        saver.save(FOOD_FOLDER_NAME, fileName);
    }
}
