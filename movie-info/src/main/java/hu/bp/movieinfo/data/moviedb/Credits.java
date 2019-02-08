package hu.bp.movieinfo.data.moviedb;

import lombok.Data;

import java.util.List;

@Data
public class Credits {
	private List<Crew> crew;
}
