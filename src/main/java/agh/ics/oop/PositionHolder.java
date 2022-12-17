package agh.ics.oop;

import java.util.Comparator;

public class PositionHolder {
    final Vector2d pos;
    final IMapElement obj;

    public PositionHolder(Vector2d newPos, IMapElement pointedElement){
        pos = newPos;
        obj = pointedElement;
    }

    static Comparator<PositionHolder> comparareXFirst = new Comparator<>() {
        @Override
        public int compare(PositionHolder o1, PositionHolder o2) {
            if(o1.pos.x == o2.pos.x)
                return o1.pos.y - o2.pos.y;
            else
                return o1.pos.x - o2.pos.x;
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    };

    static Comparator<PositionHolder> comparareYFirst = new Comparator<>() {
        @Override
        public int compare(PositionHolder o1, PositionHolder o2) {
            if(o1.pos.y == o2.pos.y)
                return o1.pos.x - o2.pos.x;
            else
                return o1.pos.y - o2.pos.y;
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    };
}
