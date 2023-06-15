import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

public class Board extends JPanel implements ActionListener {
    int score;
    String HighScore="";
    int B_HEIGHT=400;
    int B_WIDTH=400;
    int MAX_DOTS=1600;
    int DOT_SIZE=10;
    int DOTS;//size of snake
    //coordinates of bodyparts of snake
    int[] x=new int[MAX_DOTS];
    int[] y=new int[MAX_DOTS];
    int apple_x;
    int apple_y;
    //Delay tells after how much millisecond timer should increment,helps map timer to actual time
    int DELAY=100;
    //Timer directs the snake to move
    //Everytime the timer ticks it generates an action event
    Timer timer=new Timer(DELAY,this);
    //Images
    Image body,head,apple;
    boolean leftDirection;
    boolean rightDirection;
    boolean upDirection;
    boolean downDirection;
    boolean inGame;
    MainMenu menu;
    ExitMenu exitMenu;
    Board()
    {
        TAdapter tAdapter=new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH,B_HEIGHT));
        setBackground(Color.BLACK);
        initGame();
        //Adding mouse listeners to menu
        addMouseListener(menu);
        addMouseMotionListener(menu);
        addMouseMotionListener(exitMenu);
        addMouseMotionListener(exitMenu);
        loadImages();

    }
    //Initialize game
    public void initGame()
    {
        menu=new MainMenu(this);
        exitMenu=new ExitMenu(this);
    }
    //Load images from resource folder to image objects
    public void loadImages()
    {
        ImageIcon bodyImage=new ImageIcon(getClass().getResource("dot.png"));
        body=bodyImage.getImage();
        ImageIcon appleImage=new ImageIcon(getClass().getResource("apple.png"));
        apple=appleImage.getImage();
        ImageIcon headImage=new ImageIcon(getClass().getResource("head.png"));
        head=headImage.getImage();
    }
    //Draw images at snake and apple position
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(menu.active)
        {
            menu.draw(g);
            repaint();
        }
        else if(inGame)
        {
            doDrawing(g);
        }
        else if(exitMenu.isGameOver)
        {
            GameOver();
            exitMenu.draw(g);
            repaint();
        }
    }
    //Draw image
    public void doDrawing(Graphics g)
    {
        g.drawImage(apple,apple_x,apple_y,this);
        for(int i=0;i<DOTS;i++)
        {
            if(i==0)
            {
                g.drawImage(head,x[0],y[0],this);
            }
            else {
                g.drawImage(body,x[i],y[i],this);
            }
        }
    }
    //Randomise apple position
    public void locateApple()
    {
        apple_x=(int)(Math.random()*39)*DOT_SIZE;
        apple_y=(int)(Math.random()*39)*DOT_SIZE;
    }
    //Make snake move

    public void move()
    {
        for(int i=DOTS-1;i>0;i--)
        {
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(leftDirection){
            x[0]-=DOT_SIZE;
        }
        if(rightDirection){
            x[0]+=DOT_SIZE;
        }
        if(upDirection){
            y[0]-=DOT_SIZE;
        }
        if(downDirection){
            y[0]+=DOT_SIZE;
        }
    }
    //Make snake eat food
    public void checkApple()
    {
        if(apple_x==x[0] && apple_y==y[0])
        {
            DOTS++;
            locateApple();
        }
    }
    //Implement controls
    private class TAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e) {
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_LEFT && rightDirection==false)
            {
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT && leftDirection==false)
            {
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP && downDirection==false)
            {
                upDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
            if(key==KeyEvent.VK_DOWN && upDirection==false)
            {
                downDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
        }
    }
    //Check Collisions with border and body
    public void checkCollision()
    {
        //check body collision
        for(int i=1;i<DOTS;i++)
        {
            if(i>4 && x[0]==x[i] && y[0]==y[i])
            {
                inGame=false;
                exitMenu.isGameOver=true;
            }
        }
        //Collision with border
        if(x[0]<0 || x[0]>=B_WIDTH || y[0]<0 || y[0]>=B_WIDTH)
        {
            inGame=false;
            exitMenu.isGameOver=true;
        }
    }
    //Game Over
    public void GameOver()
    {
        score=(DOTS-3)*100;
        HighScore=this.GetHighScore();
        if(score>Integer.parseInt(HighScore))
        {
            HighScore=Integer.toString(score);
            File scoreFile=new File("HighScore.dat");
            if(!scoreFile.exists())
            {
                try{
                    scoreFile.createNewFile();
                }catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            FileWriter writeFile=null;
            BufferedWriter writer=null;
            try{
                writeFile=new FileWriter(scoreFile);
                writer=new BufferedWriter(writeFile);
                writer.write(HighScore);
            }catch(Exception e)
            {

            }
            finally {
                try{
                    if(writer!=null)
                    {
                        writer.close();
                    }
                }catch (IOException e)
                {

                }
            }
        }

        timer.stop();
    }
    public String GetHighScore()
    {
        FileReader fileReader=null;
        BufferedReader reader=null;
        try{
            fileReader=new FileReader("HighScore.dat");
            reader=new BufferedReader(fileReader);
            return reader.readLine();
        }catch(Exception e)
        {
            return "0";
        }
        finally
        {
            try{
                if(reader!=null) reader.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public void GameStart()
    {
        inGame=true;
        DOTS=3;
        x[0]=150;
        y[0]=150;
        //Initialize snake position
        for(int i=1;i<DOTS;i++)
        {
            x[i]=x[0]+DOT_SIZE*i;
            y[i]=y[0];
        }
        //Initialize apple position
        locateApple();
        leftDirection=true;
        rightDirection=false;
        upDirection=false;
        downDirection=false;
        timer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //check everytime if apple is consumed before snake moves
        if(inGame=true)
        {
            checkApple();
            checkCollision();
            move();
        }
        repaint();


    }
}
