public class BowlingGame {

    public int getBowlingScore(String bowlingCode) {
        //输入格式为：
        // 1："X|X|X|X|X|X|X|X|X|X||XX"
        // 2："9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||"
        // 3："5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5"
        // 4："X|7/|9-|X|-8|8/|-6|X|X|X||81"
        String[] gameInfos = bowlingCode.split("\\|\\|");//分割"||"前后分别代表正常赛与加赛
        String[] normalScores = gameInfos[0].split("\\|");//获取正常赛的分数分割

        //最多22次得分，最多11格，每隔第一次机会索引为2*i，每隔第二次机会索引为2*i+1
        //第一次分数为10时，第二次分数为0，其他两次分数为字符所代表分数
        int[] scores = new int[22];


        for(int i=0;i<normalScores.length;i++)//正常比赛分数解析
        {
            if(normalScores[i].charAt(0)=='X')//只有10分为一个字符，其余均为两个字符
            {
                scores[2*i]=10;
                scores[2*i+1]=0;
            }
            else
            {
                char score1 = normalScores[i].charAt(0);
                char score2 = normalScores[i].charAt(1);

                if(score1 == '-')//score1情况有：'数字'\'-'
                    score1 = '0';//全部转化为数字
                if(score2 == '-')//score2情况有：'数字'\'-'\'/'
                    score2 = '0';//转化为'数字'及'/'

                score1 -= '0';//将字符转化为数字
                if(score2 == '/')//第二次分数换算为分数
                    score2 = (char)(10-score1);
                else
                    score2 -= '0';

                scores[2*i] = score1;
                scores[2*i+1] = score2;
            }
        }

        //额外机会分数统计
        if(gameInfos.length==2)
        {
            char score1 = '0';
            char score2 = '0';
            score1 = gameInfos[1].charAt(0);//有额外机会时至少有一次机会
            if(gameInfos[1].length()==2)//长度为2时有分数2
            {
                score2 = gameInfos[1].charAt(1);
            }
            //统计分数1
            if(score1=='X')
                score1=10;
            else
                score1-='0';
            //统计分数2
            if(score2=='X')
                score2 = 10;
            else
                score2 -='0';
            scores[20]=score1;
            scores[21]=score2;
        }

        int finalScore = 0;
        for(int i=0;i<10;i++)//运算最终成绩
        {
            finalScore+=CalculateSingleRoundScore(scores,i);
        }
        return finalScore;
    }

    private int CalculateSingleRoundScore(int[] scores,int roundIndex)
    {
        int ret = 0;
        ret = scores[roundIndex*2]+scores[roundIndex*2+1];//本轮分数和
        if(ret==10)//10分时至少多一轮
        {
            ret += scores[roundIndex * 2 + 2];//加一轮
            if (scores[roundIndex * 2] == 10)//加两轮情况
            {
                ret += scores[roundIndex * 2 + 3];//为0不影响
                if(roundIndex==9)//最后一次结束
                    return ret;
                if (scores[roundIndex * 2 + 2] == 10) {
                    ret += scores[roundIndex * 2 + 4];//第二轮为10
                }
            }
        }
        return ret;
    }
}


