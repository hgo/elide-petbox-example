

//
//var article = client.create("user");
//article.set("name", "Guven Ozzy");
//article.set("password", "pass");
//article.set("email", "guven@asd.com");
//article.sync(function(a,b,c,d) {
//    console.info(a,b,c,d);
//});

angular.module('spa', ['schemaForm'])
.service('ApiClient',function($q){

    var options = {
      header: {
        //"content-type": "application/vnd.api+json"
        //authToken: "2ad1d6f7-e1d0-480d-86b2-dfad8af4a5b3"
      }
    };
    var client = new JsonapiClient("http://localhost:8181/api/v1/", {} );
    return {
        getUsers : function(){
            var q = $q.defer();
            client.find("user", function(err, resources) {
                if(err){
                    q.reject(err);
                } else{
                    var result = resources.map(function(resource) {
                                        return resource.toJSON();
                                });
                    q.resolve(result);
                }
            });
            return q.promise;
        },
        newUser : function(model){
            var user = client.create("user")
            for(k in model){
                user.set(k,model[k]);
            }
            return user.sync();

        }
    }
})
.controller('FormController', function($scope, ApiClient) {

    $scope.getUsers = function(){
        ApiClient.getUsers().then(function(result){
            var pre = document.querySelector('#js_getUsersResponse');
            var code = pre.querySelector('code');
            code.innerHTML = JSON.stringify(result,null, 4);
            hljs.highlightBlock(pre);
        },function(err){
            console.err(err);
        })
    }

    $scope.schema = {
        type: "object",
        properties: {
          email: { type: "string", minLength: 2, title: "Email" },
          name: { type: "string", minLength: 2, title: "Name" },
          password: { type: "string", minLength: 4 }
        }
      };

      $scope.form = [
        "email","name",{"key": "password", "type": "password"},
        {
          type: "submit",
          title: "Save"
        }
      ];

    $scope.model = {};

    $scope.onSubmit = function(userForm){
        if (userForm.$valid) {
            ApiClient.newUser($scope.model).then(function(a){
                alert('user posted', a.toJSON());
            },function(err){
                console.err(err);
            });
        }else{
            alert('not valid see console');
            console.info(userForm);
        }
    }
 });