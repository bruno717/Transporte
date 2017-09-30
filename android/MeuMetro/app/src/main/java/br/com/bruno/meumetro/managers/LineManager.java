package br.com.bruno.meumetro.managers;

import java.util.ArrayList;
import java.util.List;

import br.com.bruno.meumetro.database.LineDbHelper;
import br.com.bruno.meumetro.database.RealmDbHelper;
import br.com.bruno.meumetro.enums.StatusType;
import br.com.bruno.meumetro.models.Line;

/**
 * Created by Bruno on 10/09/2016.
 */
public class LineManager {

    public static List<Line> sortLines(List<Line> lines) {
        List<Line> list = new ArrayList<>();
        for (Line l : lines) {
            if (l.getType().equalsIgnoreCase("m") || l.getType().equalsIgnoreCase("4")) {
                list.add(l);
            }
        }
//        for (Line l : lines) {
//            if (l.getType().equalsIgnoreCase("4")) {
//                list.add(l);
//                break;
//            }
//        }
        for (Line l : lines) {
            if (l.getType().equalsIgnoreCase("c")) {
                list.add(l);
            }
        }
        return list;
    }

    public static void saveLines(List<Line> lines, StatusType type) {
        List<Line> list = LineDbHelper.getLinesByTypeStatus(type);
        if (list != null && list.size() > 0) {
            for (Line line : list) {
                RealmDbHelper.delete(line);
            }
        }
        int id = 1;
        for (Line line : lines) {
            if (line.getId() == null) {
                line.setId(id);
                id++;
            }
            line.setStatusType(type.getType());
            RealmDbHelper.save(line);
        }
    }
}
