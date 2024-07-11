import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
public class Part2 {
    static void simulateDist(int sampleSize)
    {
        //Seed Initialization
        Random rd=new Random(10);
        double d,x;
        double sum=0,mean=0.0,temp=0,std=0;
        //Sample Array Initialization
        double samples[]=new double[sampleSize];
        int idx=0;
        for(int i=0;i<sampleSize;i++)
        {
                d=rd.nextDouble();
                x=Math.pow(d,1.5);
                temp=temp+x*x;
                sum+=x;
                samples[idx++]=x;
        }
        mean=sum/sampleSize;
        std=Math.sqrt((temp-(sampleSize*mean))/(sampleSize-1));
        try
        {
            BufferedWriter writer=new BufferedWriter(new FileWriter("result.txt"));
            writer.write("Mean: "+mean+"\n");
            writer.write("Standard deviation: "+std+"\n");
            writer.write("Sample Array: "+Arrays.toString(samples));
            writer.close();
        }
        catch(IOException e){
            System.out.println("Exception Occured");
        }
    }

    static void simulateDist(int sampleSize,String distributionType,float parameter1,float parameter2){

        if(distributionType.toUpperCase().equals("BINOMIAL")){
            binomialDistribution(sampleSize,parameter1,parameter2);
        }else if(distributionType.toUpperCase().equals("NEG-BINOMIAL")){
            NBinomialDistribution(sampleSize,parameter1,parameter2);
        }else if(distributionType.toUpperCase().equals("GAMMA")){
            gammaDistribution(sampleSize,parameter1,parameter2);
        }else if(distributionType.toUpperCase().equals("UNIFORM")){
            uniformDistribution(sampleSize,parameter1,parameter2);
        }else if(distributionType.toUpperCase().equals("NORMAL")){
            normalDistribution(sampleSize,parameter1,parameter2);
        }
    }
    static void simulateDist(int sampleSize,String distributionType,float parameter1){

        if(distributionType.toUpperCase().equals("BERNOULI")){
            bernouliDistribution(sampleSize,parameter1);
        }else if(distributionType.toUpperCase().equals("GEOMETRIC")){
            geometricDistribution(sampleSize,parameter1);
        }else if(distributionType.toUpperCase().equals("EXPONENTIAL")){
            exponentialDistribution(sampleSize,parameter1);
        }else if(distributionType.toUpperCase().equals("POISSON")){
            poissonDistribution(sampleSize,parameter1);
        }
    }

    static void normalDistribution(int sampleSize,float parameter1,float parameter2){
        double samples[] = new double[sampleSize];
        int i=0;
        //Seed Initialization temp1
        Random rd1=new Random(5);
        //Seed Initialization temp2
        Random rd2=new Random(5);
        while(i<(sampleSize/2)+1){
            double temp1 = rd1.nextDouble();
            double temp2 = rd2.nextDouble();
            float var1 = (float) Math.sqrt(-2*Math.log(temp1));
            float var2 = (float) (2*Math.PI*temp2);
            double val1 = (double)var1*(Math.cos(var2));
            double val2 = var1*Math.sin(var2);
            samples[i++] = val1*parameter2 + parameter1;
            samples[i++] = val2*parameter2+parameter1;
        }

        //Function to print sample array
        printSample(samples);
    }

    //Poisson Distribution Function
    static void  poissonDistribution(int sampleSize,float parameter1){
        //Seed Initialization
        Random r=new Random(5);
        double d;
        int count=0;
        double f=0;
        //Sample Array Initialization
        int samples[]=new int[sampleSize];
        int idx=0;
        for(int i=0;i<sampleSize;i++){
            d=r.nextDouble();
            f=Math.exp(-parameter1);
            count=0;
            while(d>=f){
                f=f+Math.exp(-parameter1)*Math.pow(parameter1,count)/factorial(count);
                count++;
            }
            count--;
            samples[idx++]=count;
        }
        //Printing the samples array
        printSample(samples);
    }

    //Exponential Distribution Function
    static void exponentialDistribution(int sampleSize,float parameter1){
        //Seed Initialization
        Random r=new Random(5);
        double l=parameter1;
        //Sample Array Initialization
        double samples[]=new double[sampleSize];
        int idx=0;
        for(int i=0;i<sampleSize;i++){
            double d=r.nextDouble();
            double s=1/l;
            double mcal=Math.log(1-d);
            double cal=-(s*mcal);
            samples[idx]=cal;
            idx++;
        }
        //Function to print sample array
        printSample(samples);
    }

    //Uniform Distribution Function
    static void uniformDistribution(int sampleSize,float parameter1,float parameter2){
          
        //Seed Initialization
        Random temp=new Random(5);
        double val;
        // Sample array Initialization
        double samples[]=new double[sampleSize];
        int idx=0,i=0;
        while(i<sampleSize){
            val=temp.nextDouble();
            double x=parameter1+(val*(parameter2-parameter1));
            samples[idx++]=x;
            i++;
        }
        //Function to print the sample array 
        printSample(samples);
    }

    //Gamma Distribution Function
    static void gammaDistribution(int sampleSize,float parameter1,float parameter2){

        //Random Seed Initialization
        Random temp=new Random(5);
        double l=parameter2;
        double sum=0;
        double s,m_cal,cal;
        // sample array Initialization
        double samples[]=new double[sampleSize];
        int idx=0,i=0,j=0;
        while(i<sampleSize)
        {
            while(j<parameter1)
            {
                double val=temp.nextDouble();
                s=1/l;
                m_cal=Math.log(1-val);
                cal=-(s*m_cal);
                sum=+cal;
                j++;
            }
            samples[idx++]=sum;
            i++;
        }
        //Function to print sample array
        printSample(samples);
    }

    //Negative Binomial Distribution Function
    static void NBinomialDistribution(int sampleSize,float parameter1,float parameter2){
        
        double d;
        //Seed Initialization
        Random temp=new Random(5);
        //Samples Array Initialization
        int samples[]=new int[sampleSize];
        int flag=0;
        for(int i=0;i<sampleSize;i++){
            int count=0,num=0;
            if(i>0)
            {
                d=temp.nextDouble();
                if(d<parameter2)
                    num++;
                count++;
            }
            while(num!=parameter1)
            {
                d=temp.nextDouble();
                if(d<parameter1)
                    num++;
                count++;
            }
            samples[flag++]=count;
        }
        //printing samples
        printSample(samples);
    }

    // Geometric Distribution Function
    static void geometricDistribution(int sampleSize,float parameter1){
        
        //sample array initialization
        int samples[] = new int[5];
        //Seed Initialization
        Random random = new Random(5);
        int flag = 0;
        Double temp = random.nextDouble();
        for(int i = 0; i < sampleSize; i++)
        {
            int count = 0;
            if(i > 0)
            {
                temp=random.nextDouble();
            }
            while(temp > parameter1)
            {
                count++;
                temp=random.nextDouble();
            }
            samples[flag++]=++count;
        }
        //printing samples
       printSample(samples);
    }

    //Bernouli Distribution Function
    static void bernouliDistribution(int sampleSize,float parameter1){
        
        //Seed Initialization
        Random r=new Random(5);
        //Sample array Initialization
        int samples[]=new int[sampleSize];
        int idx=0;
        for(int i=0;i<sampleSize;i++){
            double d=r.nextDouble();
            if(d<parameter1){
                samples[idx++]=1;
            }
            else{
                samples[idx++]=0;
            }
        }
        //Prints the samples array
        printSample(samples);
    }

    // Binomial Distribution Function
        static void binomialDistribution(int sampleSize,float parameter1,float parameter2){
       //sample array initialization
        int samples[]=new int[sampleSize];
        //Seed Initialization
        Random temp=new Random(5);
        int count=0;
        int flag=0;
        for(int i=0;i<samples.length;i++){
            count=0;
            for(int j=0;j<parameter1;j++){
                double val=temp.nextDouble();
                if(val<parameter2){
                    count++;
                }
            }
            samples[flag]=count;
            flag++;
        }
        //printing samples
        printSample(samples);
    }

    // Function for Printing sample values for distributions using Integer Type
    static void printSample(int[] samples){
        double sum=0;
        double temp=0;
        double sd;
        double mean=0.0;
        for(int i=0;i<samples.length;i++){
            sum=sum+samples[i];
            temp=temp+samples[i]*samples[i];
        }
        //calculating mean value
        mean=(double)sum/samples.length;
        //calculating standard deviation
        sd=Math.sqrt(temp-(samples.length*mean))/samples.length-1;
        //try and catch block
        try
        {
            BufferedWriter writer=new BufferedWriter(new FileWriter("result.txt"));
            writer.write("Mean: "+mean+"\n");
            writer.write("Standard Deviation: "+sd+"\n");
            writer.write("Sample Array: "+Arrays.toString(samples));
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("Exception Occured!!!");
        }
    }

     // Function for Printing sample values for distributions using Double Type
    static void printSample(double[] samples){
        double sum=0;
        double temp=0;
        double sd;
        double mean=0.0;
        for(int i=0;i<samples.length;i++){
            sum=sum+samples[i];
            temp=temp+samples[i]*samples[i];
        }
        //calculating mean value
        mean=(double)sum/samples.length;
        //calculating standard deviation
        sd=Math.sqrt(temp-(samples.length*mean))/samples.length-1;
        //try and catch block
        try
        {
            BufferedWriter writer=new BufferedWriter(new FileWriter("result.txt"));
            writer.write("Mean: "+mean+"\n");
            writer.write("Standard Deviation: "+sd+"\n");
            writer.write("Sample Array: "+Arrays.toString(samples));
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("Exception Occured!!!");
        }
    }

    static int factorial(int n)
    {
        int fact=1,i=1;
        while(i<=n){
            fact=fact*i;
            i++;
        }
        return fact;
    }

    public static void main(String args[]){

        int problem = Integer.parseInt(args[0]);
         // sample size input
        int sampleSize=Integer.parseInt(args[1]);
        
      
        
        if(problem == 1 || problem == 2){
            simulateDist(sampleSize);
        }else if(problem == 3){
            // distribution type of sample
            String distributionType=args[2];
             // function for differentiating samples according to distribution type
            //simulateDist(sampleSize,distributionType,parameter1,parameter2);
            if(distributionType.toUpperCase().equals("BERNOULI")||distributionType.toUpperCase().equals("EXPONENTIAL")||distributionType.toUpperCase().equals("POISSON")||distributionType.toUpperCase().equals("GEOMETRIC"))
       {
            // parameter value 1 for distribution
            float parameter1=Float.parseFloat(args[3]);
            simulateDist(sampleSize,distributionType,parameter1);
       }
       else{
        // parameter value 1 for distribution
        float parameter1=Float.parseFloat(args[3]);
        // parameter value 2 for distribution
        float parameter2=Float.parseFloat(args[4]);
        // function for differentiating samples according to distribution type

        simulateDist(sampleSize,distributionType,parameter1,parameter2);
       }
        }
    }
}