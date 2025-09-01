{
  inputs = {
    flake-parts.url = "github:hercules-ci/flake-parts";

    nixpkgs.url = "github:NixOS/nixpkgs/nixpkgs-unstable";

    treefmt-nix = {
      url = "github:numtide/treefmt-nix";
      inputs.nixpkgs.follows = "nixpkgs";
    };
  };

  outputs = inputs @ {flake-parts, ...}:
    flake-parts.lib.mkFlake {inherit inputs;} {
      imports = [
        inputs.treefmt-nix.flakeModule
      ];
      systems = ["x86_64-linux" "aarch64-linux"];
      perSystem = {
        config,
        self',
        inputs',
        pkgs,
        system,
        ...
      }: let
        libs = with pkgs; [
          flite
          glfw3-minecraft
          libGL
          openal
          pipewire
          udev
        ];
      in {
        devShells.default = pkgs.mkShell {
          buildInputs = libs;
          LD_LIBRARY_PATH = pkgs.lib.makeLibraryPath libs;
        };

        treefmt = {
          projectRootFile = "flake.nix";

          programs.alejandra.enable = true;
        };
      };
      flake = {
      };
    };
}
