package es.upm.miw.SolitarioCelta.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Enrique on 30/10/2016.
 */

public class Result {

    private String _playerName;
    private String _dateTime;
    private int _score;

    public Result(String playerName, int score){
        _playerName = playerName;
        _score = score;
        _dateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }

    public Result(String playerName, String dateTime, int score){
        this(playerName, score);
        _score = score;
    }

    public String getPlayerName() {
        return _playerName;
    }

    public void setPlayerName(String playerName) {
        this._playerName = playerName;
    }

    public String getDateTime() {
        return _dateTime;
    }

    public void setDateTime(String dateTime) {
        this._dateTime = dateTime;
    }

    public int getScore() {
        return _score;
    }

    public void setScore(int score) {
        this._score = score;
    }

    public String toString(){
        return _playerName + ";" + _dateTime + ";" + _score;
    }
}
