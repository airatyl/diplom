package app.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DataFromPLC {
    private String address;
    private float value;

    @Override
    public String toString() {
        return "DataFromPLC{" +
                "address=" + address +
                ", value=" + value +
                '}';
    }

}
