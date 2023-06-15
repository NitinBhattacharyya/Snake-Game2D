import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;

public class ExitMenu extends MouseAdapter {
    public boolean isGameOver=false;
    private Rectangle reStartbtn;
    private String reStarttxt="Restart";
    private boolean rHighLight=false;
    private Rectangle quitbtn;
    private String quittxt="Exit";
    private boolean qHighLight=false;
    private Board board;
    private Font font;
    int rPositionY,rPositionX,qPositionY,qPositionX;
    ExitMenu(Board board)
    {
        this.board=board;
        int w,h,x,y;
        w=100;
        h=100;
        y=(board.B_HEIGHT/2)-(h/2);
        x=(board.B_WIDTH/4)-(w/2);
        rPositionY=(2*y+h)/2;
        qPositionY=rPositionY;
        rPositionX=(2*x+w)/2;
        reStartbtn=new Rectangle(x,y,w,h);
        x=(board.B_WIDTH*3/4)-(w/2);
        qPositionX=(2*x+w)/2;
        quitbtn=new Rectangle(x,y,w,h);
        font=new Font("Helvetica",Font.PLAIN,14);
    }
    public void draw(Graphics g)
    {
        Graphics2D g2d=(Graphics2D) g;
        g.setFont(font);
        g.setColor(Color.black);
        if(rHighLight)
        {
            g.setColor(Color.white);
        }
        g2d.fill(reStartbtn);
        FontMetrics fontMetrics= g.getFontMetrics(font);
        g.setColor(Color.white);
        if(rHighLight)
        {
            g.setColor(Color.black);
        }
        g.drawString(reStarttxt,rPositionX-(fontMetrics.stringWidth(reStarttxt)/2),qPositionY);
        g.setColor(Color.black);
        if(qHighLight)g.setColor(Color.white);
        g2d.fill(quitbtn);
        g.setColor(Color.white);
        if(qHighLight)
        {
            g.setColor(Color.black);
        }
        g.drawString(quittxt,qPositionX-(fontMetrics.stringWidth(quittxt)/2),qPositionY);

        g.setColor(Color.WHITE);
        g2d.draw(reStartbtn);
        g2d.draw(quitbtn);
        String msg="Game Over";
        String Score="Score:"+Integer.toString(board.score);
        Font small=new Font("Helvetica",Font.BOLD,14);
        FontMetrics Metrics=g.getFontMetrics(small);//Gets width and height of message
        g.setColor(Color.WHITE);
        g.setFont(small);
        g.drawString(msg,(board.B_WIDTH-Metrics.stringWidth(msg))/2,board.B_HEIGHT/4);
        g.drawString(Score,(board.B_WIDTH-Metrics.stringWidth(Score))/2,(3*board.B_HEIGHT)/4);
        g.drawString("HighScore:"+board.HighScore,(board.B_WIDTH-Metrics.stringWidth("HighScore:"+board.HighScore))/2,(7*board.B_HEIGHT)/8);

    }
    @Override
    public void mouseClicked(MouseEvent e)
    {
        Point p=e.getPoint();
        if(reStartbtn.contains(p))
        {
            isGameOver=false;
            board.GameStart();
        }
        else if(quitbtn.contains(p))
        {
            System.exit(0);
        }
    }
    @Override
    public void mouseMoved(MouseEvent e)
    {
        Point p=e.getPoint();
        rHighLight=reStartbtn.contains(p);
        qHighLight=quitbtn.contains(p);
    }
}
