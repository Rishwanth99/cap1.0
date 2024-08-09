console.log("Contact Page"); 

const viewContactModel = document.getElementById("view_contact_model");

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
    id: 'view_contact_model',
    override: true
  };

  const contactModel = new Modal(viewContactModel, options, instanceOptions);


  function openContactModel(){
    contactModel.show();
  }

  function closeContactModel(){
    contactModel.hide();
  }


  //load the contact data by id
  async function loadContactData(id){


    console.log(id);

    try {

        const data = await(await fetch(`http://localhost:8080/api/contact/${id}`)).json();

        console.log(data);

        document.querySelector("#contact_name").innerHTML = data.name;
        document.querySelector("#contact_email").innerHTML = data.email;
        document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
        document.querySelector("#contact_image").setAttribute("src",data.picture);
        document.querySelector("#contact_address").innerHTML = data.address;
        document.querySelector("#contact_about").innerHTML=data.description;
        
        const contactFavorite = document.querySelector("#contact_favorite");
        if (data.favorite) {
          contactFavorite.innerHTML = "Favorite Contact";

        } else {
          contactFavorite.innerHTML = "Not Favorite Contact";
        }

        document.querySelector("#contact_website").href = data.websiteLink;
        document.querySelector("#contact_website").innerHTML=data.websiteLink;
        document.querySelector("#contact_linkedIn").href=data.linkedInLink;
        document.querySelector("#contact_linkedIn").innerHTML=data.linkedInLink;




        contactModel.show();
    } catch (error) {
        
        console.log("Error:",error);
    }
  }


  async function deleteContact(id){

    Swal.fire({
      title: "Do you want to delete the contact?",
      icon:"warning",
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: "Delete",
      
    }).then((result) => {
      /* Read more about isConfirmed, isDenied below */
      if (result.isConfirmed) {
        
        const url = "http://localhost:8080/user/contact/delete/"+id;
        window.location.replace(url);
      } 
    });


  }
