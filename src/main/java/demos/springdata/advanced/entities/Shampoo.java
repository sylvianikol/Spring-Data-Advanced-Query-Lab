package demos.springdata.advanced;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "shampoos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Shampoo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

}
