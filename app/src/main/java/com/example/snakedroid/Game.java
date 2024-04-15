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
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity implements SurfaceHolder.Callback {
    private ActivityGameBinding binding;
    private final List<snake_item> snake_item_list = new ArrayList<>();
    private SurfaceView display;
    private TextView score;
    private TextView Money;

    private SurfaceHolder holder;

    private int scale =1;
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

    private Paint pointcolor = null;
    private Bitmap head=null;
    private Bitmap body=null;
    private Bitmap tail =null;
    private Bitmap food =null;
    private Bitmap diamond =null;
    private Bitmap coin =null;

    private int id_img_head;
    private int id_img_body;
    private int id_img_tail;
    private int id_img_food;

    private String nextsens;
    private boolean enable = false;
    private boolean gameover = false;
    private SharedPreferences Data;
    private Context context_game ;
    private boolean command = true;

    //gestion du serviceutiliser pour le timer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        display = binding.surfaceview;
        score = binding.score;
        Money = binding.MoneyCompteur;
        binding.surfaceview.getHolder().addCallback(this);

        //binding.imageView.setImageBitmap(BitmapFactory.decodeResource(binding.imageView.getResources(),R.drawable.map_big));


        body_size=16*scale;
        context_game = binding.getRoot().getContext();
        Data =getPreferences(context_game.MODE_PRIVATE);


        //recuperation des valeurs si vide valeur par défaut

        id_img_body =Data.getInt("IMG_BODY" ,R.drawable.snake_body);
        id_img_head = Data.getInt("IMG_HEAD" ,R.drawable.snake_head);
        id_img_tail = Data.getInt("IMG_TAIL" ,R.drawable.snake_tail);
        id_img_food =Data.getInt("IMG_FOOD" ,R.drawable.apple);



        head = get_Bitmap(id_img_head,scale);
        body = get_Bitmap(id_img_body,scale);
        tail = get_Bitmap(id_img_tail,scale);
        food = get_Bitmap(id_img_food,scale);
        coin = get_Bitmap(R.drawable.coin,scale);
        diamond = get_Bitmap(R.drawable.diamon,scale);

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
        this.holder =surfaceHolder;
        holder.setFormat(PixelFormat.TRANSPARENT);
        display.setZOrderOnTop(true);
        init();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    public void surfaceDestroyed(@NonNull SurfaceHolder holder){


    }

    private void init(){

        gameover =false;
        snake_item_list.clear();
        score.setText("0");
        Score=0;
        Money.setText("0");
        nb_coin=0;
        direction = "right";

        int start_coord_x =(body_size) * default_nb_body;

        for (int i = 0; i < default_nb_body; i++) {
            snake_item body = new snake_item(start_coord_x,body_size);
            snake_item_list.add(body);
            start_coord_x =- (body_size*2);
        }

        addfood();
        addCoin();
        moveSnake();
    }

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

    private void addCoin(){
        nb_coin ++;
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

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Money.setText(String.valueOf(nb_coin));
            }
        });

    }
    private void moveSnake(){

        if (timer == null) {
            timer = new Timer();
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameover==false && command){
                    command=false;
                int head_pos_x = snake_item_list.get(0).getPosx();
                int head_pos_y = snake_item_list.get(0).getPosY();

                if(head_pos_y == food_pos_y && head_pos_x==food_pos_x){

                    grow();
                    addfood();
                }
                if(head_pos_y == coin_pos_y && head_pos_x==coin_pos_x){


                    addCoin();
                }

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
                if(checkgameOver(head_pos_x,head_pos_y) && gameover==false){
                    gameover=true;

                    timer.purge();
                    timer.cancel();

                AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
                builder.setMessage("Your score ="+Score);
                builder.setTitle("Game Over");
                builder.setCancelable(false);
                builder.setPositiveButton("go back to menu", (dialogInterface, i) -> {
                    Intent intent= new Intent(Game.this,MainActivity.class);
                    startActivity(intent);
                });
                builder.setNeutralButton("start Again", (dialogInterface, i) -> {
                    if(enable ==false){

                        Intent intent= new Intent(Game.this,Game.class);
                        startActivity(intent);
                        enable =true;
                    }


                });
                runOnUiThread(() -> builder.show());
                int money_temp =Data.getInt("MONEY_VALUE",0);
                int moneytosend=money_temp+ nb_coin;
                SharedPreferences.Editor editor = Data.edit();
                editor.putInt("LAST_SCORE_VALUE",Score);
                editor.putInt("MONEY_VALUE",moneytosend);
                editor.apply();

                }else{
                    Bitmap head_calib =turnbitmap(snake_item_list.get(0).sens,head,scale,false);
                    canvas = holder.lockCanvas();
                    canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
                    canvas.drawBitmap(head_calib,snake_item_list.get(0).getPosx()-body_size*scale,snake_item_list.get(0).getPosY()-body_size*scale,null);

                    canvas.drawBitmap(food,food_pos_x-body_size*scale,food_pos_y-body_size*scale,null);
                    canvas.drawBitmap(coin,coin_pos_x-body_size*scale,coin_pos_y-body_size*scale,null);

                    String buffersens=snake_item_list.get(0).sens;
                    for (int i = 1; i < snake_item_list.size(); i++) {
                        String buffersenstemp = snake_item_list.get(i).sens;
                        snake_item_list.get(i).sens=buffersens;
                        buffersens = buffersenstemp;
                        int getposXtemp = snake_item_list.get(i).getPosx();
                        int getposYtemp = snake_item_list.get(i).getPosY();
                        Bitmap body_calib = turnbitmap(snake_item_list.get(i).sens,body,scale ,false)  ;
                        Bitmap tail_calib = turnbitmap(snake_item_list.get(i).sens,tail,scale,true) ;
                        snake_item_list.get(i).setPosx(head_pos_x);
                        snake_item_list.get(i).setPosY(head_pos_y);
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
                    holder.unlockCanvasAndPost(canvas);


                }
            }
            }
        },1000-speed,1000-speed);


    }


    private void grow(){

        snake_item snakeItem = new snake_item(0,0);
        snake_item_list.add(snakeItem);
        Score++;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                score.setText(String.valueOf(Score));
            }
        });
    }
    private boolean checkgameOver(int headposx,int headposy){

        boolean gameover = false;
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
    private Paint createbodycolor(){

        if(pointcolor==null){
            pointcolor = new Paint();
            pointcolor.setColor(Color.MAGENTA);
            pointcolor.setStyle(Paint.Style.FILL);
            pointcolor.setAntiAlias(true);


        }
        return pointcolor;

    }

    private Bitmap turnbitmap(String sens, Bitmap img_bitmap,int scale,boolean inverse)
    {

        Matrix matrix = new Matrix();

        Bitmap ret = img_bitmap ;
        switch (sens){

            case "right":
                if(inverse)
                {
                    matrix.postRotate(90);
                    ret= Bitmap.createBitmap(img_bitmap,0,0, img_bitmap.getWidth(),img_bitmap.getHeight(),matrix,true);
                }else{
                    matrix.postRotate(-90);
                    ret= Bitmap.createBitmap(img_bitmap,0,0, img_bitmap.getWidth(),img_bitmap.getHeight(),matrix,true);
                }

                break;


            case "left":
                if(inverse)
                {
                    matrix.postRotate(-90);
                    ret= Bitmap.createBitmap(img_bitmap,0,0, img_bitmap.getWidth(),img_bitmap.getHeight(),matrix,true);
                }else{

                    matrix.postRotate(90);
                    ret= Bitmap.createBitmap(img_bitmap,0,0, img_bitmap.getWidth(),img_bitmap.getHeight(),matrix,true);
                }

                break;

            case "up":

                if(inverse)
                {
                    matrix.postRotate(180);
                    ret= Bitmap.createBitmap(img_bitmap,0,0, img_bitmap.getWidth(),img_bitmap.getHeight(),matrix,true);
                }else{


                    ret= Bitmap.createBitmap(img_bitmap,0,0, img_bitmap.getWidth(),img_bitmap.getHeight(),matrix,true);
                }

                break;
            case "down":
                if(inverse)
                {
                    ret= Bitmap.createBitmap(img_bitmap,0,0, img_bitmap.getWidth(),img_bitmap.getHeight(),matrix,true);
                }else{

                    matrix.postRotate(180);
                    ret= Bitmap.createBitmap(img_bitmap,0,0, img_bitmap.getWidth(),img_bitmap.getHeight(),matrix,true);
                }

                break;

        }
        return ret;
    }

    private Bitmap get_Bitmap(int img, int scale){

        Bitmap tobescaledret = BitmapFactory.decodeResource(binding.keys.getResources(),img ) ;
        Bitmap ret = Bitmap.createScaledBitmap(tobescaledret,tobescaledret.getWidth()*scale,tobescaledret.getHeight()*scale,true);


        return ret;
    }
}
