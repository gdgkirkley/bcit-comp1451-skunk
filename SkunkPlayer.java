public class SkunkPlayer extends Player
{
    private boolean standing;

    public SkunkPlayer(String name, int startingScore, boolean computer)
    {
        super(name, startingScore, computer);

        standing = true;
    }

    public void setStanding(boolean standing)
    {
        this.standing = standing;
    }

    public boolean isStanding()
    {
        return this.standing;
    }
}