package testrunner;

import endgamesolver.RunLengthEncodingEndgameDatabaseTest;
import game.GameStateTest;
import game.GameTest;
import game.MoveGeneratorMetricTest;
import game.MoveGeneratorOptimizedTest;
import game.MoveGeneratorOptimizedVsMetricTest;
import game.MoveGeneratorStandardTest;
import gamevalue.GameValueTest;
import hash.BoardHashSetSpaceOptimizedTest;
import hash.BoardHashSetTest;
import hash.SymmetryCheckerTest;
import heuristic.SixBySixBoardHeuristicCalculatorTest;
import move.MoveFactoryTest;
import movecomparator.MoveComparatorTest;
import movecomparator.WinDrawHeuristicLossComparatorTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import search.alphabeta.AlphaBetaRecursiveTest;
import search.alphabeta.transposition.HashMapTranspositionTableTest;
import search.alphabeta.transposition.TwoTierElementTest;
import search.alphabeta.transposition.TwoTierTranspositionTableTest;
import board.BoardArrayBuilderTest;
import board.BoardArrayTest;
import board.BoardTest;
import board.PieceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	// board
	BoardTest.class, 
	BoardArrayTest.class,
	PieceTest.class,
	BoardArrayBuilderTest.class,
	
	// game
	GameStateTest.class,
	GameTest.class,
	MoveGeneratorMetricTest.class,
	MoveGeneratorOptimizedTest.class,
	MoveGeneratorOptimizedVsMetricTest.class,
	MoveGeneratorStandardTest.class,
	
	// gamevalue
	GameValueTest.class,
	
	// hash
	BoardHashSetSpaceOptimizedTest.class,
	BoardHashSetTest.class,
	SymmetryCheckerTest.class,
	
	// heuristic
	SixBySixBoardHeuristicCalculatorTest.class,
	
	// move
	MoveFactoryTest.class,
	
	// movecomparator
	MoveComparatorTest.class,
	WinDrawHeuristicLossComparatorTest.class,
	
	// search
	AlphaBetaRecursiveTest.class,
	
	// transposition
	HashMapTranspositionTableTest.class,
	TwoTierElementTest.class,
	TwoTierTranspositionTableTest.class,
	RunLengthEncodingEndgameDatabaseTest.class
	})
public class AllTests {
  //nothing
}