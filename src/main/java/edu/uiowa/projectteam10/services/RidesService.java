package edu.uiowa.projectteam10.services;

import edu.uiowa.projectteam10.forms.CreateRideForm;
import edu.uiowa.projectteam10.forms.RegisterForm;
import edu.uiowa.projectteam10.model.Rides;
import edu.uiowa.projectteam10.model.User;

import java.util.List;

public interface RidesService {
    Rides save(Rides rides);

    Rides saveForm(CreateRideForm rideForm);

    List<Rides> getRides();
}

