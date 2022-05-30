package com.example.grafy;

import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class GraphReader {
    private static String filename;

    public GraphReader( String filename ) {
        this.filename = filename;
    }

    public void readGraph( Graph graph) {
        LinkedList<Integer>[] vertices;
        LinkedList<Double>[] distances;
        int i = 0;
        int j = 0;
        int w;
        int k;


        String file = filename;// "Data/mygraph";
        BufferedReader br = null;
        String line = "";
        String firstline = "";

        try {
            //czytanie calego pliku linia po linii

            br = new BufferedReader(new FileReader(file));
            firstline = br.readLine();

            //najpierw pierwsza linia zeby wiersze i kolumny ogarnac
            //System.out.println("Pierwsza linia to " + firstline);
            String[] firstrow = firstline.split("\\s+");
            if (firstrow.length != 2) //tu mozna jakis wyjatek dac
                System.out.println("Zly format 1 linii");

            w = Integer.parseInt(firstrow[0]);
            k = Integer.parseInt(firstrow[1]);

           // System.out.println("w to: " + w);
           // System.out.println("k to: " + k);
           // System.out.println("Kolejne linie: ");

            vertices = new LinkedList[w * k];
            distances = new LinkedList[w * k];
            for (int f = 0; f < w * k; f++) {
                vertices[f] = new LinkedList<>();
                distances[f] = new LinkedList<>();
            }

            while( (line = br.readLine()) != null ) {
                String [] row = line.split("\\s+");
                if( row[0].equals( "" )) {                         //zeby pozbyc sie spacji na poczatku
                    row = line.trim().split("\\s+");
                }

                if( row[0].equals("")) {
                    //System.out.println("Podzielony");
                    j++;
                    i++;
                    continue;
                } else
                if( row.length == 2 ) {
                    //System.out.println("1 polaczenie");
                    row[1] = row[1].substring(1);

                    vertices[i].add(Integer.parseInt(row[0]));
                    distances[i].add(Double.parseDouble(row[1]));


                } else if( row.length == 4 ) {
                    //System.out.println("2 polaczenia");
                    row[1] = row[1].substring(1);
                    row[3] = row[3].substring(1);

                    vertices[i].add(Integer.parseInt(row[0]));
                    distances[i].add(Double.parseDouble(row[1]));

                    vertices[i].add(Integer.parseInt(row[2]));
                    distances[i].add(Double.parseDouble(row[3]));

                } else if( row.length == 6 ) {
                    //System.out.println("3 polaczenia");
                    row[1] = row[1].substring(1);
                    row[3] = row[3].substring(1);
                    row[5] = row[5].substring(1);

                    vertices[i].add(Integer.parseInt(row[0]));
                    distances[i].add(Double.parseDouble(row[1]));

                    vertices[i].add(Integer.parseInt(row[2]));
                    distances[i].add(Double.parseDouble(row[3]));

                    vertices[i].add(Integer.parseInt(row[4]));
                    distances[i].add(Double.parseDouble(row[5]));


                } else if( row.length == 8 ) {
                    // System.out.println("4 polaczenia");
                    row[1] = row[1].substring(1);
                    row[3] = row[3].substring(1);
                    row[5] = row[5].substring(1);
                    row[7] = row[7].substring(1);

                    vertices[i].add(Integer.parseInt(row[0]));
                    distances[i].add(Double.parseDouble(row[1]));

                    vertices[i].add(Integer.parseInt(row[2]));
                    distances[i].add(Double.parseDouble(row[3]));

                    vertices[i].add(Integer.parseInt(row[4]));
                    distances[i].add(Double.parseDouble(row[5]));

                    vertices[i].add(Integer.parseInt(row[6]));
                    distances[i].add(Double.parseDouble(row[7]));

                } else { //tu tez mozna wyjatek jakis zrobic
                    System.out.println("Błąd odczytu pliku");
                }
                i++;
                j++;
            }

            double min = distances[0].getFirst();
            double max = distances[0].getFirst();

            for (int i1 = 0; i1 < w * k; i1++) {
                for (int j1 = 0; j1 < distances[i1].size(); j1++) {
                    // System.out.println(distances[i].get(j));
                    if (distances[i1].get(j1) < min)
                        min = distances[i1].get(j1);

                    if (distances[i1].get(j1) > max)
                        max = distances[i1].get(j1);
                }
            }

            min = Math.floor(min);
            max = Math.ceil(max);

            //System.out.println("zaokr max to " + max);
            //System.out.println("zaokr min to " + min);

            graph.setGraph2(w, k, max, min, vertices, distances);
            //graph.printGraphAdjacencyList();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            //alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Nie znaleziono pliku " + filename );

            alert.showAndWait();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFilename() {
        return filename;
    }

}