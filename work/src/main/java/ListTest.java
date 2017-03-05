import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pansifan on 16/11/26.
 */
public class ListTest {

    public static void main(String[] args) {
        List<Integer> lists = new ArrayList<>();
        lists.add(1);
        lists.add(2);
        lists.add(3);

        System.out.println(lists);


        List<Integer> list2 = lists.stream()
                .sorted((a1,a2) -> (a1-1)-(a2-1) < 0 ? 1 : -1)
                .collect(Collectors.toList());

        System.out.println(lists);
        System.out.println(list2);

    }
}
