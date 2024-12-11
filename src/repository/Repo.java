package repository;

import model.state.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import exception.RepoException;

public class Repo implements IRepo{
    private List<PrgState> prgStateList;
    private String filename;
    private int currentIndex;

    public Repo(PrgState initialState,  String filename) {
        this.filename = filename;
        this.prgStateList = new ArrayList<PrgState>();
        this.prgStateList.add(initialState);
        this.currentIndex = 0;
    }

    //is empty
    public boolean isEmpty(){
        return this.prgStateList.isEmpty();
    }

    @Override
    public List<PrgState> getProgramList() {
        return this.prgStateList;
    }

    @Override
    public void setProgramList(List<PrgState> prgList) {
        this.prgStateList = prgList;
    }

//    @Override
//    public PrgState getCurrentProgram() {
//        return this.prgStateList.get(this.currentIndex);
//    }

    @Override
    public void setCurrentProgram(PrgState prg){
        prgStateList.set(this.currentIndex, prg);
    }

    @Override
    public List<PrgState> getStates() {
        return this.prgStateList;
    }

    @Override
    public void addPrgState(PrgState state) {
        this.prgStateList.add(state);
    }

    @Override
    public void logPrgStateExec(PrgState state) throws RepoException {
        try{
            PrintWriter logFile = new PrintWriter(new BufferedWriter( new FileWriter(this.filename, true)) );
            logFile.println(state.toString());

            logFile.close();
        }catch (IOException e){
            throw new RepoException("File does not exist");
        }
    }
}
