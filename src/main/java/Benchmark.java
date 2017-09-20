import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Benchmark {

    public void storageBenchmark(){
        Random random = new Random(2017);
        String input = "{ \n" +
                "\t\"route\":[";
        double latitude = 0;
        double longitude = 0;
        long date = 0;
        double heading = 0;
        double speed = 0;
        double acceleration = 0;
        String name = "";
        String uniqueID="";
        int routeID = 0;

        int maxPointsProcessed = 500000;
        long overallTime = 0;


        while (maxPointsProcessed >500) {
            int routeLength = random.nextInt(500);
            Date startTime = new Date();
            routeID++;


            for (int i = 0; i < routeLength; i++) {


                name = Long.toHexString(Double.doubleToLongBits(Math.random()));
                UUID.randomUUID().toString();
                latitude ++;//= random.nextDouble();
                longitude = random.nextDouble();
                date = random.nextLong();
                heading = random.nextDouble();
                speed = random.nextDouble();
                acceleration = random.nextDouble();
                uniqueID = latitude+longitude+date+heading+speed+acceleration+"";

                if (i < routeLength - 1) {
                    input += "{\"latitude\":" + latitude + ",\"longitude\":" + longitude + ",\"date\":" + date + ",\"heading\":" + heading + ",\"speed\":" + speed + ",\"acceleration\":" + acceleration +",\"uniqueID\":\""+uniqueID+ "\"},";
                } else {
                    input += "{\"latitude\":" + latitude + ",\"longitude\":" + longitude + ",\"date\":" + date + ",\"heading\":" + heading + ",\"speed\":" + speed + ",\"acceleration\":" + acceleration +",\"uniqueID\":\""+uniqueID+ "\"}],\"routeID\":"+routeID+"}";

                }


            }
            try {
                //System.out.println(input);

                URL url = new URL("http://localhost:8080/" + name + "/saveRoute");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");


                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes());
                os.flush();

            /*if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }*/

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                //System.out.println("Output from Server: ");
                while ((output = br.readLine()) != null) {
                    //System.out.println(output);
                }

                conn.disconnect();

            } catch (Exception e) {

                e.printStackTrace();
                return;

            }
            maxPointsProcessed-=routeLength;
            Date endTime = new Date();
            overallTime+= (endTime.getTime()-startTime.getTime());
            System.out.println("Number of GpsPoints Processed: "+routeLength+" Number of Left Points to be processed: "+maxPointsProcessed+ " Time consumed by Database: "+(double)(endTime.getTime()-startTime.getTime())/1000);

            input = "{ \n" +
                    "\t\"route\":[";
        }
        Date startTime = new Date();
        routeID++;

        for (int i = 0; i < maxPointsProcessed; i++) {


            name = Long.toHexString(Double.doubleToLongBits(Math.random()));
            UUID.randomUUID().toString();
            latitude ++;//= random.nextDouble();
            longitude = random.nextDouble();
            date = random.nextLong();
            heading = random.nextDouble();
            speed = random.nextDouble();
            acceleration = random.nextDouble();
            uniqueID = UUID.randomUUID().toString();

            if (i < maxPointsProcessed - 1) {
                input += "{\"latitude\":" + latitude + ",\"longitude\":" + longitude + ",\"date\":" + date + ",\"heading\":" + heading + ",\"speed\":" + speed + ",\"acceleration\":" + acceleration +",\"uniqueID\":\""+uniqueID+ "\"},";
            } else {
                input += "{\"latitude\":" + latitude + ",\"longitude\":" + longitude + ",\"date\":" + date + ",\"heading\":" + heading + ",\"speed\":" + speed + ",\"acceleration\":" + acceleration +",\"uniqueID\":\""+uniqueID+ "\"}],\"routeID\":"+routeID+"}";

            }


        }
        try {
            //System.out.println(input);

            URL url = new URL("http://localhost:8080/" + name + "/saveRoute");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");


            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            /*if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }*/

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            //System.out.println("Output from Server: ");
            while ((output = br.readLine()) != null) {
                //System.out.println(output);
            }

            conn.disconnect();

        } catch (Exception e) {

            e.printStackTrace();
            return;

        }
        Date endTime = new Date();
        overallTime+= (endTime.getTime()-startTime.getTime());
        System.out.println("Number of GpsPoints Processed: "+maxPointsProcessed+"Number of Left Points to be Processed: "+maxPointsProcessed+" Time consumed by Database: "+(double)(endTime.getTime()-startTime.getTime())/1000);
        System.out.println((double)overallTime/1000);
        maxPointsProcessed-=maxPointsProcessed;
        input = "{ \n" +
                "\t\"route\":[";
    }





    private void distanceBenchmark() throws Exception {
        long overallTime = 0;
        Random random = new Random(2017);
        double latitude = 0;
        double longitude = 0;
        int distance = 0;

        for(int i = 0; i < 50000; i++){


            Date startTime = new Date();

        latitude = random.nextDouble();
        longitude = random.nextDouble();
        distance = random.nextInt(400);

        //System.out.println(input);

        URL url = new URL("http://localhost:8080/withinDistanceCall?latitude=" + latitude + "&longitude=" + longitude + "&distance=1");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header

        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'GET' request to URL : " + url);
        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        //System.out.println(response.toString());

        Date endTime = new Date();
        overallTime += (endTime.getTime() - startTime.getTime());
        System.out.println((double)(endTime.getTime() - startTime.getTime())/1000 + " seconds for "+ i+"th entry!");
        }
        System.out.println((double)overallTime/1000+ " Seconds ");
    }





    public static void main(String[] args) {

        Benchmark bench = new Benchmark();

        bench.storageBenchmark();
        try {
            //bench.distanceBenchmark();
        }catch(Exception e){

        }



    }



}



