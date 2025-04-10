package ru.nutsalhan87.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse implements Serializable {
    @XmlElement(required = true)
    public int status;
    @XmlElement(required = true)
    public String body;
}
