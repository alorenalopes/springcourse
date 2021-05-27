package com.lorena.springcourse.domain;

import java.io.Serializable;

import javax.annotation.processing.GeneratedValue;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity(name = "request_file")
public class RequestFile implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(columnDefinition = "text", nullable = false)
    private String location;

    @Getter(onMethod = @__({@JsonIgnore}))
    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;
}