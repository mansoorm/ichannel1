package lk.sliit.dcws;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Response;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.text.DecimalFormat;
import javax.ws.rs.core.MediaType;




@Path("doctors/")
@Singleton
public class DoctorResource {

    private ArrayList<Doctor> doctors = new ArrayList<Doctor>();

    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDoctors() {
        System.out.println("GET all Doctors (text)");
        String result = "";
        
        if(this.doctors.size() == 0)
            result = "none";
        
        for(Doctor doctor: this.doctors)
        {
            result += "ID = " + doctor.id + ", Name = " + doctor.name + System.getProperty("line.separator");
        }
        return result;
    }
   
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Doctor[] getDoctorsJson() {
        System.out.println("GET all Doctors (JSON)");
        Doctor[] result = new Doctor[1];
        return this.doctors.toArray(result);
    }

    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("specialization/{sp}")
    public String getDoctorsBySpecialization(@PathParam("sp") String specialization) {
        System.out.println("GET all Doctors by specialization (text): " + specialization);
        String result = "";
        
        if(this.doctors.size() == 0)
            result = "none";
        
        for(Doctor doctor: this.doctors)
        {
            if(doctor.specialization.toUpperCase().startsWith(specialization.toUpperCase()))
                result += "ID = " + doctor.id + ", Name = " + doctor.name + System.getProperty("line.separator");
        }
        return result;
    }

        
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("specialization/{sp}")
    public Doctor[] getDoctorsBySpecializationJson(@PathParam("sp") String specialization) {
        System.out.println("GET all Doctors by specialization (JSON): " + specialization);
        ArrayList<Doctor> matches = new ArrayList<Doctor>();
        Doctor[] result = new Doctor[1];

        for(Doctor doctor: this.doctors)
        {
            if(doctor.specialization.toUpperCase().startsWith(specialization.toUpperCase()))
                matches.add(doctor);
        }

        return matches.toArray(result);
    }

       
    private Doctor findDoctor(String id)
    {
        for(Doctor doctor : this.doctors)
        {
            if(id.equalsIgnoreCase(doctor.id))
            {
                return doctor; 
            }
        }
        return null;
    }

    private String getNextDoctorId()
    {
        DecimalFormat formatter = new DecimalFormat("doc000"); // we generate ID numbers and send it through this formatter to generate ID's

        int test = this.doctors.size() + 1; // start with 1 when we're empty, or with the most likely next value in sequence
        
        // loop until we find the next unused ID to return
        while(true)
        {
            String testId = formatter.format(test);
            if(this.findDoctor(testId) == null)
                return testId; // this ID is not in use so it's available!
            else
                test++; // it's in use, test the next value
        }
    }

    // ==================================== My Code ============================================================================================== 
     // TODO: implement getDoctorsByLastName() matching path /ichannel/doctor/lastname/{lastname} 
    // for both text and JSON. {lastname} should be a startsWith so that you could search for
    // ichannel/doctor/lastname/jaya and get Jayasekara, Jayaratna, Jayawardena etc.
    // 10% credit.
     // getDoctorsByLastName - Text
    
    @Path("lastname/{lastname}/") 
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDoctorsByLastName(@PathParam("lastname") String lastName) {
        System.out.println("GET all Doctors by LastName(text): " + lastName);
        String result = "";
        
     if(this.doctors.size() == 0)
            result = "none";
        
        for(Doctor doctor: this.doctors)
        {
        
           if(doctor.lastName.toUpperCase().startsWith(lastName.toUpperCase()))
                result += "ID = " + doctor.id + ", LastName = " + doctor.lastName + System.getProperty("line.separator");
        }
        return result;
    }
    
   //JSON - Mansoor getDoctorsByLastNameJson
    
    @Path("lastname/{lastname}/") 
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Doctor[] getDoctorsByLastNameJson(@PathParam("lastname") String LastName) {
        System.out.println("GET all Doctors by specialization (JSON): " + LastName);
        ArrayList<Doctor> matches = new ArrayList<Doctor>();
        Doctor[] result = new Doctor[1];

        for(Doctor doctor: this.doctors)
        {
            if(doctor.lastName.toUpperCase().startsWith(LastName.toUpperCase()))
                matches.add(doctor);
        }

        return matches.toArray(result);
    }
    
    
    
    // TODO: implement getDoctorsByHospital() matching path /ichannel/doctor/hospital/{id} 
    // for both text and JSON. {id} should be a hospital ID.
    // 5% credit.
    
    
    // Text
    @Path("hospitals/{id}/") 
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getDoctorsByHospital(@PathParam("id") String id) {
           
    System.out.println("GET all Doctors by  Hospital ID(text): " + id);
     
    String result = "";
        
        if(this.doctors.size() == 0)
            result = "none";
        
        for(Doctor doctor: this.doctors)
        {
           
            for (int i=0; i < doctor.hospitals.length;i++){
                 if(doctor.hospitals[i].equals(id))
                 result += "ID = " + doctor.id + ", LastName = " + doctor.lastName + System.getProperty("line.separator");
            }
        }
        return result;
            
        
    }
    
    
    // JSON
    
  
   @Path("hospitals/{id}/") 
   @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Doctor[] getDoctorsByHospitalJson(@PathParam("id") String id) {
              
    System.out.println("GET all Doctors by  Hospital ID(text): " + id);
     ArrayList<Doctor> matches = new ArrayList<Doctor>();
         Doctor[] result = new Doctor[1];

        for(Doctor doctor: this.doctors)
        {
            for (int i=0; i < doctor.hospitals.length;i++){
                 if(doctor.hospitals[i].equals(id))
                  matches.add(doctor);
            }
        }

        return matches.toArray(result);
            
       
    }
    
    
    
    
    
    // TODO: implement createDoctor() for the HTTP POST action - 5% credit
       
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDoctor(Doctor doctor) { 
     doctor.id = this.getNextDoctorId();
        
        String message = "POST Doctor: " + doctor.name + " with new ID: " + doctor.id;
        System.out.println(message);

        this.doctors.add(doctor);
        return Response.status(201).entity(message).build();
    }
    
   
    // TODO: implement updateDoctor() for the HTTP PUT action - 5% credit 

    @Path("/{id}/")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDoctor(Doctor doctor, @PathParam("id") String id) {
        System.out.println("PUT Doctor: " + id + " with " + doctor.name);
        Doctor stored = this.findDoctor(id);
        if(stored != null)
        {
            String oldName = stored.name;
            stored.name = doctor.name;
            String Name = oldName + " renamed to " + doctor.name;
            System.out.println(Name);
            
            String oldLname = stored.lastName;
            stored.name = doctor.lastName;
            String Lname = oldLname + " renamed to " + doctor.lastName;
            System.out.println(Lname);
                     
            
            return Response.status(200).entity(Name + Lname).build();
        }
        else
        {
            return Response.status(404).entity(doctor.id + " is not found. Use PUT with a correct ID to modify or use POST to create new entry.").build();
        }
    }

    
    // TODO: implement deleteDoctor() for the HTTP DELETE action - 5% credit
    
    
    
    @Path("/{id}/")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteDoctor(@PathParam("id") String id) {
        System.out.println("DELETE Doctor: " + id);
        Doctor doctor = this.findDoctor(id);
        if(doctor != null)
        {
            this.doctors.remove(doctor);
            String message = "Deleted doctor " + doctor.id + doctor.name;
            System.out.println(message);
            return Response.status(200).entity(message).build();
        }
        else
        {
            return Response.status(404).entity(id + " is not found. Use DELETE with a correct ID to delete.").build();     
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
