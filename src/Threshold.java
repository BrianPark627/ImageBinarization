public class Threshold {

    private int[] occurrence;
    private int max;

    public Threshold(int[] occurrence, int max){
        this.occurrence = new int[occurrence.length];
        this.max = max;
        for(int i = 0; i < occurrence.length; i++){
            this.occurrence[i] = occurrence[i];
        }
    }

    private float getWeight(int T){
        float weight = 0;
        for(int i = 0; i <= T; i++){
            weight += occurrence[i];
        }
        return weight/ (float)(Math.pow(occurrence.length,2));
    }

    private float differenceInMeans(int T){
        float difference1 = 0, difference2 = 0, mean1 = 0, mean2 = 0;
        int denom1 = 0, denom2 = 0;
        for(int i = 0; i < occurrence.length; i++) {
            if(i <= T) {
                difference1 += i * occurrence[i];
                denom1 += occurrence[i];
            }
            else{
                difference2 += i* occurrence[i];
                denom2 += occurrence[i];
            }
        }
        if(denom1 == 0) {
            mean1 = 0;
            mean2 = difference2 / denom2;
        }
        else if (denom2 == 0){
            mean1 = difference1 / denom1;
            mean2 = 0;
        }
        else {
            mean1 = difference1 / denom1;
            mean2 = difference2 / denom2;
        }

        return mean1-mean2;
    }

    public float getBetweenClassVariance(int T){

        float betweenClassVariance = getWeight(T) * (1-getWeight(T)) * (float) Math.pow(differenceInMeans(T), 2);

        if(T == occurrence.length - 1)
            return betweenClassVariance;

        else if(betweenClassVariance > getBetweenClassVariance(T + 1)){
            max = T;
        }
        return betweenClassVariance;
    }

    public int getThreshold(){
        return max;
    }
}
