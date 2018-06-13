pragma solidity ^0.4.19;

contract StoryBookFactory {

    uint dnaDigits = 16;//https://gitlab.com/yangboz/iStoryBook/wikis/Vladimir_Propp
    uint dnaModulus = 10 ** dnaDigits;

    struct StoryBook {
        string name;
        uint dna;
    }

    StoryBook[] public storyBooks;

    function _createStoryBook(string _name, uint _dna) private {
        storyBooks.push(Zombie(_name, _dna));
        // 这里触发事件
    }

    function _generateRandomDna(string _str) private view returns (uint) {
        uint rand = uint(keccak256(_str));
        return rand % dnaModulus;
    }

    function createRandomStoryBook(string _name) public {
        uint randDna = _generateRandomDna(_name);
        _createStoryBook(_name, randDna);
    }

}
