package com.example.snakedroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.snakedroid.databinding.ActivityGameBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity implements SurfaceHolder.Callback {
    private ActivityGameBinding binding;
    private final List<snake_item> snake_item_list = new ArrayList<>();
    private SurfaceView display;
    private TextView score;
    private TextView Money;
    private Intent thisintent;
    private SurfaceHolder holder;

    private final int scale = 1;
    private String direction = "down";
    private static int body_size=20;
    private static final int default_nb_body=3;

    private static final int speed = 700;
    private int Score = 0;
    private int nb_coin =-1;
    private int food_pos_x, food_pos_y;
    private int coin_pos_x,coin_pos_y;
    private Timer timer;

    private Canvas canvas = null;

    private Bitmap head=null;
    private Bitmap body=null;
    private Bitmap tail =null;
    private Bitmap food =null;
    private Bitmap coin =null;

    private boolean enable = false;
    private boolean gameover = false;
    private SharedPreferences Data;
    private boolean command = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //// Configuration de l'affichage du jeu, initialisation des variables

        display = binding.surfaceview;
        score = binding.score;
        Money = binding.MoneyCompteur;
        binding.surfaceview.getHolder().addCallback(this);

        body_size=16*scale;

        Context context_game = binding.getRoot().getContext();

        //// Récupération des données sauvegardées

        Data = getPreferences(context_game.MODE_PRIVATE);
        thisintent = getIntent();

        //recuperation des valeurs si vide valeur par défaut

        int id_img_body = Data.getInt("IMG_BODY", R.drawable.snake_body);
        int id_img_head = Data.getInt("IMG_HEAD", R.drawable.snake_head);
        int id_img_tail = Data.getInt("IMG_TAIL", R.drawable.snake_tail);
        int id_img_food = Data.getInt("IMG_FOOD", R.drawable.apple);

        //// Initialisation des images du jeu

        head = get_Bitmap(id_img_head);
        body = get_Bitmap(id_img_body);
        tail = get_Bitmap(id_img_tail);
        food = get_Bitmap(id_img_food);
        coin = get_Bitmap(R.drawable.coin);
        Bitmap diamond = get_Bitmap(R.drawable.diamon);


        //// Gestion des touches de déplacement du serpent

        binding.keyUp.setOnClickListener(view -> {


            if (!direction.equals("up")) {
                direction = "down";

            }
        });

        binding.keyLeft.setOnClickListener(view -> {
            if (!direction.equals("right")) {
                direction = "left";

            }
        });

        binding.keyRight.setOnClickListener(view -> {
            if (!direction.equals("left")) {
                direction = "right";

            }
        });

        binding.keyDown.setOnClickListener(view -> {
            if (!direction.equals("down")) {
                direction = "up";

            }
        });

    }




    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        //// Initialisation du board de jeu

        this.holder =surfaceHolder;
        holder.setFormat(PixelFormat.TRANSPARENT);
        display.setZOrderOnTop(true);
        init();
    }

    //// Font partie de l'interface SurfaceHolder.Callback
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    public void surfaceDestroyed(@NonNull SurfaceHolder holder){
    }

    ///////////////////////////////////////////////////////////


    //// Initialisation du jeu
    private void init(){

        gameover =false;
        snake_item_list.clear();
        score.setText("0");
        Score=0;
        Money.setText("0");
        nb_coin=0;
        direction = "right";

        int start_coord_x =(body_size) * default_nb_body; // position de départ du serpent

        //// Création du serpent

        for (int i = 0; i < default_nb_body; i++) {
            snake_item body = new snake_item(start_coord_x,body_size);
            snake_item_list.add(body);
            start_coord_x =- (body_size*2);
        }

        //// Ajout de la nourriture et des pièces

        addfood();
        addCoin();
        moveSnake();
    }

    //// Ajout de la nourriture sur le board

    private void addfood(){
        int displaywidth = display.getWidth()-(body_size *2 );
        int displayheight = display.getHeight()-(body_size *2 );

        int random_coord_x = new Random().nextInt(displaywidth/body_size );
        int random_coord_y = new Random().nextInt(displayheight/body_size );

        if((random_coord_x % 2)!=0){

            random_coord_x++;
        }
        if((random_coord_y % 2)!=0){

            random_coord_y++;
        }
        food_pos_x  =(body_size* random_coord_x)+body_size;
        food_pos_y = (body_size * random_coord_y)+body_size;
    }

    //// Ajout de pièces sur le board

    private void addCoin(){
        nb_coin++;
        int displaywidth = display.getWidth()-(body_size *2 );
        int displayheight = display.getHeight()-(body_size *2 );

        int random_coord_x = new Random().nextInt(displaywidth/body_size );
        int random_coord_y = new Random().nextInt(displayheight/body_size );

        if((random_coord_x % 2)!=0){

            random_coord_x++;
        }
        if((random_coord_y % 2)!=0){

            random_coord_y++;
        }
        coin_pos_x  =(body_size* random_coord_x)+body_size;
        coin_pos_y = (body_size * random_coord_y)+body_size;

        //// Affichage du nombre de pièces

        runOnUiThread(() -> Money.setText(String.valueOf(nb_coin)));

    }

    //// Gestion du déplacement du serpent
    private void moveSnake(){

        //// Initialisation du timer

        if (timer == null) {
            timer = new Timer();
        }

        //// Incrémentation du timer et actualisation du board
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                //// Gestion du game over

                if (!gameover && command){
                    command=false;


                //// Gestion de la collision avec la nourriture et les pièces

                int head_pos_x = snake_item_list.get(0).getPosx();
                int head_pos_y = snake_item_list.get(0).getPosY();

                if(head_pos_y == food_pos_y && head_pos_x==food_pos_x){
                    grow();
                    addfood();
                }
                if(head_pos_y == coin_pos_y && head_pos_x==coin_pos_x){
                    addCoin();
                }


                //// Actualisation de la position du serpent

                switch (direction){

                    case "right":

                        snake_item_list.get(0).setPosx((head_pos_x +(body_size*2)));
                        snake_item_list.get(0).setPosY((head_pos_y));
                        snake_item_list.get(0).sens="right";
                        break;


                    case "left":
                        snake_item_list.get(0).setPosx((head_pos_x -(body_size*2)));
                        snake_item_list.get(0).setPosY((head_pos_y));
                        snake_item_list.get(0).sens="left";
                        break;

                    case "up":
                        snake_item_list.get(0).setPosx((head_pos_x ));
                        snake_item_list.get(0).setPosY((head_pos_y)+(body_size*2));
                        snake_item_list.get(0).sens="up";
                        break;
                    case "down":
                        snake_item_list.get(0).setPosx((head_pos_x));
                        snake_item_list.get(0).setPosY((head_pos_y- (body_size*2)));
                        snake_item_list.get(0).sens="down";
                        break;

                    }

                //// Gestion du game over

                if(checkgameOver(head_pos_x,head_pos_y) && !gameover){
                    gameover=true;

                    timer.purge(); //// Arrêt du timer
                    timer.cancel();

                    //// Récupération du nom du joueur

                    Bundle bundle = thisintent.getExtras();
                    assert bundle != null;
                    String nom =(String) bundle.getSerializable("name");

                    save(nom);

                    //// Construction du message de fin de partie

                AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
                builder.setMessage(nom +" Votre score = "+Score);
                builder.setTitle("Game Over");
                builder.setCancelable(false);
                builder.setPositiveButton("Revenir au Menu", (dialogInterface, i) -> {
                    Intent intent= new Intent(Game.this,MainActivity.class);

                    startActivity(intent);
                });
                builder.setNeutralButton("Recommencer", (dialogInterface, i) -> {
                    if(!enable){

                        Intent intent= new Intent(Game.this,Game.class);
                        intent.putExtra("name",nom);
                        startActivity(intent);
                        enable =true;
                        Game.this.finish();

                    }


                });

                //// Affichage du message de fin de partie
                runOnUiThread(builder::show);


                }else{

                    //// Actualisation du board

                    Bitmap head_calib = turnbitmap(snake_item_list.get(0).sens,head, false);   //// Rotation de la tête du serpent
                    canvas = holder.lockCanvas();   //// Verrouillage du canvas
                    canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);   //// Effacement du canvas
                    canvas.drawBitmap(head_calib,snake_item_list.get(0).getPosx()-body_size*scale,snake_item_list.get(0).getPosY()-body_size*scale,null);   //// Affichage de la tête du serpent

                    canvas.drawBitmap(food,food_pos_x-body_size*scale,food_pos_y-body_size*scale,null);  //// Affichage de la nourriture
                    canvas.drawBitmap(coin,coin_pos_x-body_size*scale,coin_pos_y-body_size*scale,null);  //// Affichage des pièces

                    String buffersens=snake_item_list.get(0).sens;  //// Rotation du corps du serpent

                    //// Rotation du corps du serpent

                    for (int i = 1; i < snake_item_list.size(); i++) {
                        String buffersenstemp = snake_item_list.get(i).sens;
                        snake_item_list.get(i).sens=buffersens;
                        buffersens = buffersenstemp;
                        int getposXtemp = snake_item_list.get(i).getPosx();
                        int getposYtemp = snake_item_list.get(i).getPosY();
                        Bitmap body_calib = turnbitmap(snake_item_list.get(i).sens,body, false)  ;
                        Bitmap tail_calib = turnbitmap(snake_item_list.get(i).sens,tail, true) ;
                        snake_item_list.get(i).setPosx(head_pos_x);
                        snake_item_list.get(i).setPosY(head_pos_y);

                        //// Affichage du corps du serpent

                        if (i==(snake_item_list.size()-1))
                        {
                            canvas.drawBitmap(tail_calib,snake_item_list.get(i).getPosx()-body_size*scale,snake_item_list.get(i).getPosY()-body_size*scale,null);
                        }
                        else
                        {
                            canvas.drawBitmap(body_calib,snake_item_list.get(i).getPosx()-body_size*scale,snake_item_list.get(i).getPosY()-body_size*scale,null);
                        }

                        head_pos_y = getposYtemp;
                        head_pos_x = getposXtemp;
                        command =true;

                    }
                    holder.unlockCanvasAndPost(canvas);     //// Déverrouillage du canvas


                }
            }
            }
        },1000-speed,1000-speed);   //// Actualisation du board


    }


    //// Grossissement du serpent

    private void grow(){

        snake_item snakeItem = new snake_item(0,0);
        snake_item_list.add(snakeItem);
        Score++;
        runOnUiThread(() -> score.setText(String.valueOf(Score)));
    }

    //// Vérification de la fin de partie
    private boolean checkgameOver(int headposx,int headposy){

        boolean gameover = false;

        //// Si le serpent touche les bords du board ou se mord la queue, la partie est terminée

        if(snake_item_list.get(0).getPosx()<0 || snake_item_list.get(0).getPosY()<0 || snake_item_list.get(0).getPosx() >= display.getWidth() || snake_item_list.get(0).getPosY() >= display.getHeight()){
            gameover =true;

        }else {

            for (int i = 0; i < snake_item_list.size(); i++) {

                if(headposx==snake_item_list.get(i).getPosx() &&
                        headposy==snake_item_list.get(i).getPosY()){

                    gameover =true;
                    break;
                }
            }
        }
        return  gameover;
    }

    //// Rotation des images du serpent
    private Bitmap turnbitmap(String sens, Bitmap img_bitmap, boolean inverse)
    {

        Matrix matrix = new Matrix();

        Bitmap ret = img_bitmap ;


        switch (sens){

            case "right":
                if(inverse)
                {
                    matrix.postRotate(90);  //// Rotation de 90°
                }else{
                    matrix.postRotate(-90); //// Rotation de -90°
                }
                ret= Bitmap.createBitmap(img_bitmap,0,0, img_bitmap.getWidth(),img_bitmap.getHeight(),matrix,true); //// Création de l'image

                break;


            case "left":
                if(inverse)
                {
                    matrix.postRotate(-90);
                }else{

                    matrix.postRotate(90);
                }
                ret= Bitmap.createBitmap(img_bitmap,0,0, img_bitmap.getWidth(),img_bitmap.getHeight(),matrix,true);

                break;

            case "up":

                if(inverse)
                {
                    matrix.postRotate(180);
                }
                ret= Bitmap.createBitmap(img_bitmap,0,0, img_bitmap.getWidth(),img_bitmap.getHeight(),matrix,true);

                break;
            case "down":
                if (!inverse) {

                    matrix.postRotate(180);
                }
                ret= Bitmap.createBitmap(img_bitmap,0,0, img_bitmap.getWidth(),img_bitmap.getHeight(),matrix,true);

                break;

        }
        return ret;
    }


    //// Récupération des images du jeu
    private Bitmap get_Bitmap(int img){

        Bitmap tobescaledret = BitmapFactory.decodeResource(binding.keys.getResources(),img ) ; //// Récupération de l'image à redimensionner


        return Bitmap.createScaledBitmap(tobescaledret, tobescaledret.getWidth(), tobescaledret.getHeight(),true);
    }

    //// Sauvegarde des scores, des noms des joueurs et des pièces
    private void save(String username){

        int money_temp =Data.getInt("MONEY_VALUE",0);
        int moneytosend=money_temp+ nb_coin;
        int player_count = Data.getInt("NB_PLAYER",0);
        SharedPreferences.Editor editor = Data.edit();
        boolean save_done =false;
        for (int i = 0; i < player_count; i++) {
            String key_name ="NAME"+i;
            if (Objects.equals(username, Data.getString(key_name, ""))){
                String key_score = "SCORE"+i;
                editor.putInt(key_score,Score);
                    save_done=true;

            }
        }
        if (!save_done){

            player_count++;
            String scoreid = "SCORE"+player_count;
            String nameid  = "NAME"+player_count;
            editor.putInt(scoreid,Score);
            editor.putString(nameid,username);
            editor.putInt("NB_PLAYER",player_count);
            editor.putInt("MONEY_VALUE",moneytosend);

        }
        editor.apply();

    }
}
