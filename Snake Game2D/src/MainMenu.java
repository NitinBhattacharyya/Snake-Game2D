import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainMenu extends MouseAdapter {
    //To check if main menu is active or not
    public boolean active;
    //Button play
    Rectangle playbtn;
    String playtxt="Play";
    boolean pHighLight=false;
    //Button Quit
    Rectangle quitbtn;
    String quittxt="Exit";
    boolean qHighLight=false;
    Font font;
    Board board;
    int pPositionX,pPositionY,qPositionX,qPositionY;
    MainMenu(Board board)
    {
        this.board=board;
        active=true;
//        board.GameStart();
        int w,h,x,y;
        w=100;
        h=100;
        y=(board.B_HEIGHT/2)-(h/2);
        x=(board.B_WIDTH/4)-(w/2);
        pPositionY=(2*y+h)/2;
        qPositionY=pPositionY;
        pPositionX=(2*x+w)/2;
        playbtn=new Rectangle(x,y,w,h);
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
        if(pHighLight)
        {
            g.setColor(Color.white);
        }
        g2d.fill(playbtn);
        FontMetrics fontMetrics= g.getFontMetrics(font);
        g.setColor(Color.white);
        if(pHighLight)
        {
            g.setColor(Color.black);
        }
        g.drawString(playtxt,pPositionX-(fontMetrics.stringWidth(playtxt)/2),qPositionY);
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
        g2d.draw(playbtn);
        g2d.draw(quitbtn);
    }
    @Override
    public void mouseClicked(MouseEvent e)
    {
        Point p=e.getPoint();
        if(playbtn.contains(p))
        {
            board.GameStart();
            active=false;
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
        pHighLight=playbtn.contains(p);
        qHighLight=quitbtn.contains(p);
    }
}
