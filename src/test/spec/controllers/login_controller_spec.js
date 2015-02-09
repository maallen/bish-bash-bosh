describe("controller: LoginController", function() {

  beforeEach(function() {
     module("myAppModule");
  });
    
  beforeEach(inject(function($controller, $rootScope, $location, $httpBackend, AuthenticationService,UserService) {
      
    this.$location = $location;
    this.$httpBackend = $httpBackend;
    this.scope = $rootScope.$new();
    this.redirect = spyOn($location, 'path');
    this.$controller = $controller;
    
    this.AuthenticationService = AuthenticationService;
    this.UserService = UserService;    
      
    $controller('LoginCtrl', {
        
      $scope: this.scope,
      $location: $location,
      $controller: $controller,
      AuthenticationService: AuthenticationService,
      UserService: UserService,
        
    });
      
  }));

    // Not working yet - Need to look into this
  /*afterEach(function() {
      
    this.$http.verifyNoOutstandingRequest();
    this.$http.verifyNoOutstandingExpectation();
  });*/
    

  describe("Successfully logging in", function() {
      
    it("should redirect you to /add", function() {
        
        this.scope.newMember = {email: "kerry@gmail.com", password: "kerry"};
        
        console.log(this.scope.newMember);
        
        // Arrange
        this.$httpBackend.expectPOST('rest/members/login',this.scope.newMember).respond(200);
        
        // Act
        this.scope.login('kerry@gmail.com', 'kerry');
        
        this.$httpBackend.flush();
        
        //Assert
        expect(this.redirect).toHaveBeenCalledWith('/add');
        
    });
  });
});