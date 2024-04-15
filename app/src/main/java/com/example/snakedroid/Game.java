package com.example.snakedroid;

import android.content.Intent;
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

    private SurfaceHolder holder;

    private int scale =2;
    private String direction = "down";
    private static int body_size=16;
    private static final int default_nb_body=3;

    private static final int speed = 800;
    private int Score = 0;
    private int food_pos_x, food_pos_y;
    private Timer timer;

    private Canvas canvas = null;

    private Paint pointcolor = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        display = binding.surfaceview;
        score = binding.score;
        binding.surfaceview.getHolder().addCallback(this);

        body_size=16*scale;



    }
    @Override
    public void onResume() {

        super.onResume();
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
        //holder.setFormat(PixelFormat.TRANSPARENT);
        init();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    public void surfaceDestroyed(@NonNull SurfaceHolder holder){


    }

    private void init(){

        snake_item_list.clear();
        score.setText("0");
        Score=0;
        direction = "right";

        int start_coord_x =(body_size) * default_nb_body;

        for (int i = 0; i < default_nb_body; i++) {
            snake_item body = new snake_item(start_coord_x,body_size);
            snake_item_list.add(body);
            start_coord_x =- (body_size*2);
        }

        addfood();
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
    private void moveSnake(){

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int head_pos_x = snake_item_list.get(0).getPosx();
                int head_pos_y = snake_item_list.get(0).getPosY();

                if(head_pos_y == food_pos_y && head_pos_x==food_pos_x){

                    grow();
                    addfood();
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
                if(checkgameOver(head_pos_x,head_pos_y)){

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
                builder.setNeutralButton("start Again", (dialogInterface, i) -> init());
                runOnUiThread(() -> builder.show());

                }else{
                    Bitmap head =turnbitmap(snake_item_list.get(0).sens,R.drawable.snake_head,false,scale);
                    canvas = holder.lockCanvas();
                    canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
                    canvas.drawBitmap(head,snake_item_list.get(0).getPosx()-16*scale,snake_item_list.get(0).getPosY()-16*scale,null);
                    //canvas.drawCircle(snake_item_list.get(0).getPosx(),snake_item_list.get(0).getPosY(),body_size,createbodycolor());
                    canvas.drawCircle(food_pos_x,food_pos_y,body_size,createbodycolor());
                    String buffersens=snake_item_list.get(0).sens;
                    for (int i = 1; i < snake_item_list.size(); i++) {
                        String buffersenstemp = snake_item_list.get(i).sens;
                        snake_item_list.get(i).sens=buffersens;
                        buffersens = buffersenstemp;
                        int getposXtemp = snake_item_list.get(i).getPosx();
                        int getposYtemp = snake_item_list.get(i).getPosY();
                        Bitmap body = turnbitmap(snake_item_list.get(i).sens,R.drawable.snake_body,false,scale)  ;
                        Bitmap tail = turnbitmap(snake_item_list.get(i).sens,R.drawable.snake_tail,true,scale) ;
                        snake_item_list.get(i).setPosx(head_pos_x);
                        snake_item_list.get(i).setPosY(head_pos_y);
                        if (i==(snake_item_list.size()-1))
                        {

                            canvas.drawBitmap(tail,snake_item_list.get(i).getPosx()-16*scale,snake_item_list.get(i).getPosY()-16*scale,null);
                        }
                        else
                        {
                            canvas.drawBitmap(body,snake_item_list.get(i).getPosx()-16*scale,snake_item_list.get(i).getPosY()-16*scale,null);
                        }

                       //canvas.drawCircle(snake_item_list.get(i).getPosx(),snake_item_list.get(i).getPosY(),body_size,createbodycolor());

                        head_pos_y = getposYtemp;
                        head_pos_x = getposXtemp;


                    }
                    holder.unlockCanvasAndPost(canvas);


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

    private Bitmap turnbitmap(String sens, int img,boolean inverse,int scale)
    {
        Bitmap tobescaledhead = BitmapFactory.decodeResource(binding.keys.getResources(),img ) ;
        Bitmap head = Bitmap.createScaledBitmap(tobescaledhead,tobescaledhead.getWidth()*scale,tobescaledhead.getHeight()*scale,true);
        Matrix matrix = new Matrix();

        Bitmap ret = head ;
        switch (sens){

            case "right":
                if(inverse)
                {
                    matrix.postRotate(90);
                    ret= Bitmap.createBitmap(head,0,0, head.getWidth(),head.getHeight(),matrix,true);
                }else{
                    matrix.postRotate(-90);
                    ret= Bitmap.createBitmap(head,0,0, head.getWidth(),head.getHeight(),matrix,true);
                }

                break;


            case "left":
                if(inverse)
                {
                    matrix.postRotate(-90);
                    ret= Bitmap.createBitmap(head,0,0, head.getWidth(),head.getHeight(),matrix,true);
                }else{

                    matrix.postRotate(90);
                    ret= Bitmap.createBitmap(head,0,0, head.getWidth(),head.getHeight(),matrix,true);
                }

                break;

            case "up":

                if(inverse)
                {
                    matrix.postRotate(180);
                    ret= Bitmap.createBitmap(head,0,0, head.getWidth(),head.getHeight(),matrix,true);
                }else{


                    ret= Bitmap.createBitmap(head,0,0, head.getWidth(),head.getHeight(),matrix,true);
                }

                break;
            case "down":
                if(inverse)
                {
                    ret= Bitmap.createBitmap(head,0,0, head.getWidth(),head.getHeight(),matrix,true);
                }else{

                    matrix.postRotate(180);
                    ret= Bitmap.createBitmap(head,0,0, head.getWidth(),head.getHeight(),matrix,true);
                }

                break;

        }
        return ret;
    }
}
