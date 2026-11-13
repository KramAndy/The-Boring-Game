package util;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class LoadSave {
	
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String URM_BUTTONS = "urm_buttons.png";
	public static final String SOUND_BUTTONS = "sound_button.png";
	public static final String VOLUME_BUTTONS = "volume_buttons.png";
	public static final String BACKGROUND_MENU_IMG = "menu_background_FINAL.png";
	public static final String PLAYING_BACKGROUND_IMG = "bg_image.png";
	public static final String LARGE_CLOUD = "big_clouds.png";
	public static final String SMALL_CLOUD = "small_cloud_1.png";
	public static final String LIVES_IMAGE = "lives.png";
	public static final String LIVES_ICON = "lives_icon.png";
	public static final String POTION_IMAGE = "potions_sprites.png";
	public static final String PIRATE_ATLAS = "enemy_atlas.png";
	public static final String DEATH_ATLAS = "death.png";
	public static final String SPAWN_ATLAS = "spawn.png";
	public static final String COMPLETED_IMAGE = "completed_sprite.png";
	public static final String TRAP_ATLAS = "spike.png";
	public static final String CANNON_ATLAS = "cannon_atlas.png";
	public static final String CANNON_BALL = "ball.png";
	public static final String GAME_OVER_BG = "game_over.png";
	public static final String OPTIONS_BG = "options_background.png";
	public static final String STORY = "story.png";
	
	public static BufferedImage GetSpriteAtlas(String filePath) {
	    BufferedImage img = null;
	    InputStream is = LoadSave.class.getResourceAsStream("/" + filePath);
	    
	    if (is == null) {
	        System.out.println("Error: Could not find the file " + filePath);
	        return null;
	    }
	    
	    try {
	        img = ImageIO.read(is);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (is != null) is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return img;
	}
	public static BufferedImage[] GetAllLevels() {
	    // The folder where levels are stored
	    String path = "/lvls"; // Path to the folder inside the JAR or classpath
	    
	    // Load the resources from the JAR or directory
	    List<BufferedImage> imgs = new ArrayList<>();
	    
	    try {
	        // Try loading the levels as resources in a JAR
	        InputStream is = LoadSave.class.getResourceAsStream(path);
	        if (is == null) {
	            System.out.println("Error: Levels directory does not exist or is not a directory.");
	            return new BufferedImage[0];  // Return an empty array
	        }
	        
	       
	        
	        for (int i = 1; i <= 5; i++) { // Adjust based on the number of levels you have
	            String levelPath = path + "/" + i + ".png";
	            InputStream levelStream = LoadSave.class.getResourceAsStream(levelPath);
	            if (levelStream != null) {
	                BufferedImage levelImg = ImageIO.read(levelStream);
	                imgs.add(levelImg);
	                levelStream.close();
	            }
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return imgs.toArray(new BufferedImage[0]);
	}
	
	


}